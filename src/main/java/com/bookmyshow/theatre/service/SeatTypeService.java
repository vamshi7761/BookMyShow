package com.bookmyshow.theatre.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.theatre.dto.SeatTypeDto;
import com.bookmyshow.theatre.exception.SeatTypeValidationException;
import com.bookmyshow.theatre.model.SeatType;
import com.bookmyshow.theatre.repository.SeatTypeRepository;
import com.bookmyshow.theatre.validations.SeatTypeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;


    public OutputCommand createSeatType(InputCommand request) {
        SeatTypeDto seatTypeDto = (SeatTypeDto) request.getInputData();

        SeatTypeValidation.validateSeatType(seatTypeDto);

        SeatType seatType = new SeatType();
        seatType.setType(seatTypeDto.getType());

        SeatType savedSeatType = seatTypeRepository.save(seatType);
        return OutputCommand.builder()
                .outputData(savedSeatType)
                .build();
    }

    public OutputCommand updateSeatType(InputCommand request) {
        SeatTypeDto seatTypeDto = (SeatTypeDto) request.getInputData();

        SeatTypeValidation.validateSeatTypeId(seatTypeDto);

        SeatType existingSeatType = seatTypeRepository.findById(seatTypeDto.getSeatTypeId())
                .orElseThrow(() -> new SeatTypeValidationException("Seat type not found with ID: " + seatTypeDto.getSeatTypeId()));

        if (seatTypeDto.getType() != null && !seatTypeDto.getType().trim().isEmpty()) {
            existingSeatType.setType(seatTypeDto.getType());
        }

        SeatType updatedSeatType = seatTypeRepository.save(existingSeatType);
        return OutputCommand.builder()
                .outputData(updatedSeatType)
                .build();
    }

    public OutputCommand deleteSeatType(InputCommand request) {
        SeatTypeDto seatTypeDto = (SeatTypeDto) request.getInputData();

        SeatTypeValidation.validateSeatTypeId(seatTypeDto);

        SeatType existingSeatType = seatTypeRepository.findById(seatTypeDto.getSeatTypeId())
                .orElseThrow(() -> new SeatTypeValidationException("Seat type not found with ID: " + seatTypeDto.getSeatTypeId()));

        seatTypeRepository.deleteById(seatTypeDto.getSeatTypeId());
        return OutputCommand.builder()
                .outputData("Seat type deleted successfully")
                .build();
    }

    public OutputCommand getSeatType(InputCommand request) {
        SeatTypeDto seatTypeDto = (SeatTypeDto) request.getInputData();

        SeatType existingSeatType = null;

        if(seatTypeDto.getSeatTypeId() != null){
            existingSeatType = seatTypeRepository.findById(seatTypeDto.getSeatTypeId())
                    .orElse(null);
        }

        if(existingSeatType == null){
            SeatTypeValidation.validateSeatType(seatTypeDto);

            existingSeatType = seatTypeRepository.findByType(seatTypeDto.getType())
                    .orElseThrow(() -> new SeatTypeValidationException("Seat type not found with ID: " + seatTypeDto.getSeatTypeId()));
        }

        return OutputCommand.builder()
                .outputData(existingSeatType)
                .build();
    }
}