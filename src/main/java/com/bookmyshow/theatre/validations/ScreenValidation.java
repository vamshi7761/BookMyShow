package com.bookmyshow.theatre.validations;

import com.bookmyshow.theatre.dto.ScreenDto;
import com.bookmyshow.theatre.exception.ScreenValidationException;
import com.bookmyshow.theatre.exception.TheatreValidationException;

public class ScreenValidation {

    public static void validateScreenOnCreate(ScreenDto screenDto) {

        if (screenDto == null ) {
            throw new TheatreValidationException("Theatre data cannot be null");
        }

        if (screenDto.getTheatreId() == null) {
            throw new ScreenValidationException("Theatre ID is required");
        }

        if (screenDto.getName() == null || screenDto.getName().trim().isEmpty()) {
            throw new ScreenValidationException("Screen name is required");
        }

        if (screenDto.getFeatures().isEmpty() || screenDto.getFeatures().size() == 0) {
            throw new ScreenValidationException("Screen features are required");
        }

        if (screenDto.getStatus().isEmpty() || screenDto.getStatus().trim().isEmpty()) {
            throw new ScreenValidationException("Screen status is required");
        }
    }

    public static void validateScreenOnGet(ScreenDto screenDto) {

        if (screenDto == null ) {
            throw new TheatreValidationException("Theatre data cannot be null");
        }

        if (screenDto.getScreenId() == null) {
            throw new ScreenValidationException("Screen ID is required");
        }
    }
}
