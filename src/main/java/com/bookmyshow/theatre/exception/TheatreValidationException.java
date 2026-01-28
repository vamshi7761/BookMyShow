package com.bookmyshow.theatre.exception;

public class TheatreValidationException extends RuntimeException {

    public TheatreValidationException(String message) {
        super(message);
    }

    public TheatreValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}