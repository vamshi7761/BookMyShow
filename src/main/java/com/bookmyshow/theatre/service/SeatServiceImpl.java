package com.bookmyshow.theatre.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.SeatDtoList;
import com.bookmyshow.theatre.dto.SeatTypeDto;
import com.bookmyshow.theatre.exception.SeatValidationException;
import com.bookmyshow.theatre.model.Screen;
import com.bookmyshow.theatre.model.Seat;
import com.bookmyshow.theatre.model.SeatType;
import com.bookmyshow.theatre.repository.ScreenRepository;
import com.bookmyshow.theatre.repository.SeatRepository;
import com.bookmyshow.theatre.validations.SeatValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements  SeatService{

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final SeatTypeService seatTypeService;

    @Override
    public OutputCommand addSeatsToScreen(InputCommand inputCommand) {

        SeatDtoList seatDtoList = (SeatDtoList) inputCommand.getInputData();

        SeatValidation.validateOnAddSeatsToScreen(seatDtoList);

        Screen screen = screenRepository.findById(seatDtoList.getScreenId())
                .orElseThrow(() -> new SeatValidationException("Screen with ID " + seatDtoList.getScreenId() + " not found"));

        List<Seat> seats = seatDtoList.getSeats().stream().map(seatDto -> {
            Seat seat = new Seat();
            seat.setRowId(seatDto.getRowId());
            seat.setColumnId(seatDto.getColumnId());
            seat.setScreenId(screen);
            String seatTypeName = seatDto.getSeatTypeName();
            SeatTypeDto seatTypeDto = SeatTypeDto
                    .builder()
                    .type(seatTypeName)
                    .build();
            InputCommand seatTypeCommand = InputCommand
                    .builder()
                    .inputData(seatTypeDto)
                    .build();
            SeatType seatType = (SeatType) seatTypeService.getSeatType(seatTypeCommand).getOutputData();
            seat.setSeatType(seatType);
            return seat;
        }).toList();

        seatRepository.saveAll(seats);

        return OutputCommand.builder()
                .outputData(seats)
                .build();
    }

    @Override
    public OutputCommand getSeatsByScreenId(InputCommand inputCommand) {
        Long screenId = (Long) inputCommand.getInputData();
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new SeatValidationException("Screen with ID " + screenId + " not found"));

        List<Seat> seats = seatRepository.findByScreenIdId(screenId);
        return OutputCommand.builder()
                .outputData(seats)
                .build();
    }

    @Override
    public OutputCommand updateSeatsInScreen(InputCommand inputCommand) {
        SeatDtoList seatDtoList = (SeatDtoList) inputCommand.getInputData();

        Long screenId = seatDtoList.getScreenId();

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new SeatValidationException("Screen with ID " + screenId + " not found"));

        List<Seat> updatedSeats = seatDtoList.getSeats().stream().map(seatDto -> {
            Optional<Seat> seatByPosition = Optional.ofNullable(seatRepository.findByScreenIdIdAndRowIdAndColumnId(
                    screenId,
                    seatDto.getRowId(),
                    seatDto.getColumnId()
            ));

            Seat seat;
            if (seatByPosition.isPresent()) {
                seat = seatByPosition.get();
            } else {
                seat = seatRepository.findById(seatDto.getSeatId())
                        .orElseThrow(() -> new SeatValidationException(
                                "Seat not found at Row: " + seatDto.getRowId() +
                                        ", Column: " + seatDto.getColumnId()));
            }

            // Check if position needs to be updated
            if (seatDto.getNewRowId() != null || seatDto.getNewColumnId() != null) {
                String targetRowId = seatDto.getNewRowId() != null ? seatDto.getNewRowId() : seat.getRowId();
                Integer targetColumnId = seatDto.getNewColumnId() != null ? seatDto.getNewColumnId() : seat.getColumnId();

                // Check if a seat already exists at the target position
                Optional<Seat> existingSeatAtTarget = Optional.ofNullable(
                        seatRepository.findByScreenIdIdAndRowIdAndColumnId(
                                screenId,
                                targetRowId,
                                targetColumnId
                        )
                );

                // If a seat exists at target position and it's not the same seat, delete it
                if (existingSeatAtTarget.isPresent() && !existingSeatAtTarget.get().getId().equals(seat.getId())) {
                    seatRepository.delete(existingSeatAtTarget.get());
                }

                // Update the seat's position
                seat.setRowId(targetRowId);
                seat.setColumnId(targetColumnId);
            }

            // Update seat properties
            if (seatDto.getSeatTypeName() != null && !seatDto.getSeatTypeName().isEmpty()) {
                SeatTypeDto seatTypeDto = SeatTypeDto.builder()
                        .type(seatDto.getSeatTypeName())
                        .build();
                InputCommand seatTypeCommand = InputCommand.builder()
                        .inputData(seatTypeDto)
                        .build();
                SeatType seatType = (SeatType) seatTypeService.getSeatType(seatTypeCommand).getOutputData();
                seat.setSeatType(seatType);
            }
            return seat;
        }).toList();
        seatRepository.saveAll(updatedSeats);

        return OutputCommand.builder()
                .outputData("Successfully updated " + updatedSeats.size() + " seat(s)")
                .build();
    }

    @Override
    public OutputCommand deleteSeatsFromScreen(InputCommand inputCommand) {
        SeatDtoList seatDtoList = (SeatDtoList) inputCommand.getInputData();

        Long screenId = seatDtoList.getScreenId();

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new SeatValidationException("Screen with ID " + screenId + " not found"));

        List<Seat> seatsToDelete = seatDtoList.getSeats().stream().map(seatDto -> {
            Optional<Seat> seatByPosition = Optional.ofNullable(seatRepository.findByScreenIdIdAndRowIdAndColumnId(
                    screenId,
                    seatDto.getRowId(),
                    seatDto.getColumnId()
            ));

            if (seatByPosition.isPresent()) {
                return seatByPosition.get();
            }

            return seatRepository.findById(seatDto.getSeatId())
                    .orElseThrow(() -> new SeatValidationException(
                            "Seat not found at Row: " + seatDto.getRowId() +
                                    ", Column: " + seatDto.getColumnId()));
        }).toList();

        seatRepository.deleteAll(seatsToDelete);

        return OutputCommand.builder()
                .outputData("Successfully deleted " + seatsToDelete.size() + " seat(s)")
                .build();
    }
}
