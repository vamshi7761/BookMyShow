package com.bookmyshow.theatre.validations;

import com.bookmyshow.theatre.dto.TheatreDto;
import com.bookmyshow.theatre.exception.TheatreValidationException;

public class TheatreValidation {

    public static void validateOnTheatreCreate(TheatreDto request) {

        if (request == null) {
            throw new TheatreValidationException("Theatre data cannot be null");
        }

        if (request.getCityId() == null) {
            throw new TheatreValidationException("City ID is mandatory");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new TheatreValidationException("Theatre name is mandatory");
        }

        if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            throw new TheatreValidationException("Theatre address is mandatory");
        }

        if(request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            throw new TheatreValidationException("Theatre status is mandatory");
        }
    }

    public static void validateOnGetTheatre(TheatreDto request) {

        if (request == null) {
            throw new TheatreValidationException("Theatre data cannot be null");
        }

        if (request.getTheatreId() == null) {
            throw new TheatreValidationException("Theatre ID is mandatory");
        }

    }
}
