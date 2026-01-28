
package com.bookmyshow.showtime.validations;

import com.bookmyshow.showtime.dto.ShowSeatTypeDto;
import com.bookmyshow.showtime.exception.ShowSeatTypeValidationException;

import java.util.Objects;

public class ShowSeatTypeValidation {

    public static void validateOnCreate(ShowSeatTypeDto dto) {
        if (dto == null) throw new ShowSeatTypeValidationException("ShowSeatTypeDto is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
        Objects.requireNonNull(dto.getSeatTypeId(), "seatTypeId is required");
        Objects.requireNonNull(dto.getPrice(), "price is required");
        if (dto.getPrice() <= 0) {
            throw new ShowSeatTypeValidationException("price must be greater than 0");
        }
    }

    public static void validateOnUpdate(ShowSeatTypeDto dto) {
        if (dto == null) throw new ShowSeatTypeValidationException("ShowSeatTypeDto is required");
        Objects.requireNonNull(dto.getShowSeatTypeId(), "showSeatTypeId is required");
        // Other fields optional; constraints validated where present in service
        if (dto.getPrice() != null && dto.getPrice() <= 0) {
            throw new ShowSeatTypeValidationException("price must be greater than 0 when provided");
        }
    }

    public static void validateOnGet(ShowSeatTypeDto dto) {
        if (dto == null) throw new ShowSeatTypeValidationException("ShowSeatTypeDto is required");
        Objects.requireNonNull(dto.getShowSeatTypeId(), "showSeatTypeId is required");
    }

    public static void validateListByShow(ShowSeatTypeDto dto) {
        if (dto == null) throw new ShowSeatTypeValidationException("ShowSeatTypeDto is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
    }
}
