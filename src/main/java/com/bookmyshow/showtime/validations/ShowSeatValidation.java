
package com.bookmyshow.showtime.validations;

import com.bookmyshow.showtime.dto.ShowSeatDto;
import com.bookmyshow.showtime.exception.ShowSeatValidationException;

import java.util.Objects;

public class ShowSeatValidation {

    public static void validateGenerate(ShowSeatDto dto) {
        if (dto == null) throw new ShowSeatValidationException("ShowSeatGenerateDto is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
    }

    public static void validateQuery(ShowSeatDto dto) {
        if (dto == null) throw new ShowSeatValidationException("ShowSeatQueryDto is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
    }
}
