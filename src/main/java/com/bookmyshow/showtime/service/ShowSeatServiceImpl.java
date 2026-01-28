
package com.bookmyshow.showtime.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.showtime.dto.ShowSeatDto;
import com.bookmyshow.showtime.enums.ShowSeatStatus;
import com.bookmyshow.showtime.exception.ShowSeatValidationException;
import com.bookmyshow.showtime.model.Show;
import com.bookmyshow.showtime.model.ShowSeat;
import com.bookmyshow.showtime.repository.ShowRepository;
import com.bookmyshow.showtime.repository.ShowSeatRepository;
import com.bookmyshow.showtime.validations.ShowSeatValidation;
import com.bookmyshow.theatre.model.Seat;
import com.bookmyshow.theatre.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowSeatServiceImpl implements ShowSeatService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final SeatRepository seatRepository;

    /**
     * Generates ShowSeat rows for the given showId by copying seats from the show's screen.
     * Idempotent: skips seats that already have a ShowSeat entry.
     */
    @Override
    public OutputCommand generateShowSeatsForShow(InputCommand request) {
        ShowSeatDto dto = (ShowSeatDto) request.getInputData();

        ShowSeatValidation.validateGenerate(dto);

        Show show = showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowSeatValidationException("Show not found with ID: " + dto.getShowId()));

        Long screenId = show.getScreen().getId();

        // Fetch base seats of the screen
        List<Seat> screenSeats = seatRepository.findByScreenIdId(screenId);
        if (screenSeats.isEmpty()) {
            throw new ShowSeatValidationException("No base seats found for screen ID: " + screenId);
        }

        // Create ShowSeat entries for seats that are not yet mapped
        List<ShowSeat> toCreate = new ArrayList<>();
        for (Seat seat : screenSeats) {
            boolean exists = showSeatRepository.existsByShow_IdAndSeat_Id(show.getId(), seat.getId());
            if (!exists) {
                ShowSeat ss = new ShowSeat();
                ss.setShow(show);
                ss.setSeat(seat);
                ss.setStatus(ShowSeatStatus.AVAILABLE);
                toCreate.add(ss);
            }
        }

        List<ShowSeat> saved = toCreate.isEmpty() ? List.of() : showSeatRepository.saveAll(toCreate);

        return OutputCommand.builder()
                .outputData(saved) // return the newly created ones; you could also return count
                .build();
    }

    @Override
    public OutputCommand getShowSeats(InputCommand request) {

        ShowSeatDto dto = (ShowSeatDto) request.getInputData();
        ShowSeatValidation.validateQuery(dto);

        // Validate show exists (optional but recommended)
        showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowSeatValidationException("Show not found with ID: " + dto.getShowId()));

        List<ShowSeat> seats = showSeatRepository.findByShow_Id(dto.getShowId());
        return OutputCommand.builder()
                .outputData(seats)
                .build();
    }

    @Override
    public OutputCommand getAvailableShowSeats(InputCommand request) {
        ShowSeatDto dto = (ShowSeatDto) request.getInputData();
        ShowSeatValidation.validateQuery(dto);

        showRepository.findById(dto.getShowId())
                .orElseThrow(() -> new ShowSeatValidationException("Show not found with ID: " + dto.getShowId()));

        List<ShowSeat> available = showSeatRepository.findByShow_IdAndStatus(dto.getShowId(), ShowSeatStatus.AVAILABLE);
        return OutputCommand.builder()
                .outputData(available)
                .build();
    }
}

