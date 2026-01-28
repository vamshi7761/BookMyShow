
package com.bookmyshow.booking.validations;

import com.bookmyshow.booking.dto.BookingDto;
import com.bookmyshow.booking.exception.BookingValidationException;

import java.util.Objects;

public class BookingValidation {

    public static void validateOnCreate(BookingDto dto) {
        if (dto == null) throw new BookingValidationException("BookingDto is required");
        Objects.requireNonNull(dto.getUserId(), "userId is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
        Objects.requireNonNull(dto.getShowSeatIds(), "showSeatIds are required");
        if (dto.getShowSeatIds().isEmpty()) {
            throw new BookingValidationException("At least one seat must be selected");
        }
    }


    public static void validateByUser(BookingDto dto) {
        if (dto == null) throw new BookingValidationException("BookingQueryDto is required");
        Objects.requireNonNull(dto.getUserId(), "userId is required");
    }

}
