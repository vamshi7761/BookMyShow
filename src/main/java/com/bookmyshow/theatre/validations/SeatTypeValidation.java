package com.bookmyshow.theatre.validations;


import com.bookmyshow.theatre.dto.SeatTypeDto;
import com.bookmyshow.theatre.exception.SeatTypeValidationException;

public class SeatTypeValidation {

    public static void validateSeatType(SeatTypeDto seatTypeDto) {
        if (seatTypeDto.getType() == null || seatTypeDto.getType().trim().isEmpty()) {
            throw new SeatTypeValidationException("Seat type is required");
        }
    }

    public static void validateSeatTypeId(SeatTypeDto seatTypeDto) {
        if (seatTypeDto.getSeatTypeId() == null) {
            throw new SeatTypeValidationException("Seat type ID is required");
        }
    }
}