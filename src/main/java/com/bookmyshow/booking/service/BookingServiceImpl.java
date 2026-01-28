
package com.bookmyshow.booking.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.booking.dto.BookingDto;
import com.bookmyshow.booking.exception.BookingValidationException;
import com.bookmyshow.booking.model.Booking;
import com.bookmyshow.booking.enums.BookingStatus;
import com.bookmyshow.booking.repository.BookingRepository;
import com.bookmyshow.showtime.enums.ShowSeatStatus;
import com.bookmyshow.showtime.model.Show;
import com.bookmyshow.showtime.model.ShowSeat;
import com.bookmyshow.showtime.model.ShowSeatType;
import com.bookmyshow.showtime.repository.ShowRepository;
import com.bookmyshow.showtime.repository.ShowSeatRepository;
import com.bookmyshow.showtime.repository.ShowSeatTypeRepository;
import com.bookmyshow.user.model.User;
import com.bookmyshow.user.repository.UserRepository;
import com.bookmyshow.booking.validations.BookingValidation;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.OptimisticLockException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;
    private final BookingRepository bookingRepository;

    /**
     * Creates a booking:
     *  - Validates request
     *  - Loads user & show
     *  - Reserves (BLOCKS) requested seats
     *  - Calculates amount from ShowSeatType pricing for this show
     *  - Persists and returns the booking
     *
     * If a concurrency conflict occurs (optimistic lock), we DO NOT RETRY.
     * We catch the exception and present a clear error to the user.
     */
    @Override
    @Transactional
    public OutputCommand createBooking(InputCommand request) {
        BookingDto dto = (BookingDto) request.getInputData();

        // Validate request DTO
        BookingValidation.validateOnCreate(dto);

        // 1) Fetch & validate user
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BookingValidationException("Invalid user: " + dto.getUserId()));

        // 2) Fetch & validate show
        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new BookingValidationException("Invalid show: " + dto.getShowId()));

        // 3–5) Reserve (BLOCK) seats
        List<ShowSeat> reservedSeats;
        try {
            reservedSeats = reserveSeatsJavaOnly(dto.getShowSeatIds(), show);
        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
            throw new BookingValidationException("Seats were updated by another user. Please reselect and try again.");
        }

        // 6) Calculate amount based on ShowSeatType pricing for this show
        double totalAmount = calculateAmountBySeatTypePricing(reservedSeats, show.getId());

        // 7) Create & persist booking
        Booking booking = Booking
                .builder()
                .bookingStatus(BookingStatus.IN_PROGRESS)
                .amount(totalAmount)
                .showSeats(reservedSeats)
                .show(show)
                .user(user)
                .build();

        Booking saved = bookingRepository.save(booking);

        return OutputCommand.builder()
                .outputData(saved)
                .build();
    }

    /**
     * Reserves (BLOCKS) selected seats for the given show using pure Java/JPA:
     *  - Reads seats by IDs
     *  - Validates all belong to the show
     *  - Checks availability / block expiry
     *  - Sets status to BLOCKED (will set blockedAt via entity setter)
     *  - Saves updated seats
     *
     * If another transaction modifies the same seats before we flush,
     * JPA/Hibernate will throw an OptimisticLockException due to @Version in BaseModel.
     */
    @Transactional
    protected List<ShowSeat> reserveSeatsJavaOnly(List<Long> showSeatIds, Show show) {
        List<ShowSeat> seats = showSeatRepository.findAllById(showSeatIds);

        // Basic validation: all requested seats should exist and belong to the given show
        if (seats.size() != showSeatIds.size()
                || seats.stream().anyMatch(ss -> !Objects.equals(ss.getShow().getId(), show.getId()))) {
            throw new BookingValidationException("Invalid seat selection for the given show");
        }

        // Validate each seat can be blocked
        seats.forEach(this::ensureAvailableOrExpiredBlocked);

        // Set BLOCKED (the entity setter will stamp blockedAt)
        seats.forEach(ss -> ss.setStatus(ShowSeatStatus.BLOCKED));

        // Persist updated seats; @Version will protect from concurrent lost updates
        return showSeatRepository.saveAll(seats);
    }

    /**
     * Seat must be AVAILABLE, or BLOCKED with an expired hold.
     * Throws BookingValidationException if not eligible to block.
     */
    private void ensureAvailableOrExpiredBlocked(ShowSeat showSeat) {
        if (showSeat.getStatus() == ShowSeatStatus.BOOKED) {
            throw new BookingValidationException("Seat already booked: " + showSeat.getId());
        }

        if (showSeat.getStatus() == ShowSeatStatus.BLOCKED) {
            // If blockedAt missing, treat as currently blocked
            if (showSeat.getBlockedAt() == null) {
                throw new BookingValidationException("Seat currently blocked: " + showSeat.getId());
            }

            // minutes since blocked = blockedAt -> now
            long minutesSinceBlocked = Duration.between(
                    showSeat.getBlockedAt().toInstant(),
                    new Date().toInstant()
            ).toMinutes();

            // Within 5 minutes window, it's still blocked
            if (minutesSinceBlocked < 5) {
                throw new BookingValidationException("Seat currently blocked: " + showSeat.getId());
            }
            // Else block expired; allow re-blocking. setStatus(BLOCKED) will refresh blockedAt.
        }
    }

    /**
     * Calculates total amount by resolving each reserved seat's SeatType
     * against ShowSeatType pricing configured for the show.
     */
    private double calculateAmountBySeatTypePricing(List<ShowSeat> seats, Long showId) {
        // Fetch all price rows for this show
        List<ShowSeatType> priceRows = showSeatTypeRepository.findByShow_Id(showId);
        if (priceRows == null || priceRows.isEmpty()) {
            throw new BookingValidationException("No seat type prices configured for show: " + showId);
        }

        // Build price map: seatTypeId -> price
        Map<Long, Double> priceBySeatTypeId = priceRows.stream()
                .collect(Collectors.toMap(
                        sst -> sst.getSeatType().getId(),
                        ShowSeatType::getPrice
                ));

        double total = 0.0;
        for (ShowSeat ss : seats) {
            if (ss.getSeat() == null || ss.getSeat().getSeatType() == null) {
                throw new BookingValidationException("Seat type not set for seat: " + (ss.getSeat() != null ? ss.getSeat().getId() : "unknown"));
            }
            Long seatTypeId = ss.getSeat().getSeatType().getId();
            Double price = priceBySeatTypeId.get(seatTypeId);
            if (price == null) {
                throw new BookingValidationException("Price not configured for seat type: " + seatTypeId + " in show: " + showId);
            }
            total += price;
        }
        return total;
    }


    @Override
    @Transactional(readOnly = true)
    public OutputCommand getBookingsByUser(InputCommand request) {
        BookingDto dto = (BookingDto) request.getInputData();
        BookingValidation.validateByUser(dto);

        // Optional: validate user exists (gives clearer errors)
        userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BookingValidationException("Invalid user: " + dto.getUserId()));

        // You can use ordered version to return latest-first:
        // List<Booking> bookings = bookingRepository.findByUser_IdOrderByCreatedAtDesc(dto.getUserId());
        List<Booking> bookings = bookingRepository.findByUser_Id(dto.getUserId());

        return OutputCommand.builder()
                .outputData(bookings)
                .build();
    }

}
