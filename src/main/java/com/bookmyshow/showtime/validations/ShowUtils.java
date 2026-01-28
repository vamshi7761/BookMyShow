package com.bookmyshow.showtime.validations;

import com.bookmyshow.showtime.dto.ShowDto;
import com.bookmyshow.showtime.exception.ShowValidationException;
import java.util.Objects;

public class ShowUtils {

    public static void validateShowOnCreate(ShowDto dto) {
        if (dto == null) throw new ShowValidationException("ShowDto is required");
        Objects.requireNonNull(dto.getMovieId(), "movieId is required");
        Objects.requireNonNull(dto.getTheatreId(), "theatreId is required");
        Objects.requireNonNull(dto.getScreenId(), "screenId is required");
        Objects.requireNonNull(dto.getStartTime(), "startTime is required");
        Objects.requireNonNull(dto.getLanguage(), "language is required");
    }

    public static void validateShowOnUpdate(ShowDto dto) {
        if (dto == null) throw new ShowValidationException("ShowDto is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
        // Other fields optional; validate when present in service.
    }

    public static void validateShowOnGet(ShowDto dto) {
        if (dto == null) throw new ShowValidationException("ShowDto is required");
        Objects.requireNonNull(dto.getShowId(), "showId is required");
    }


}
