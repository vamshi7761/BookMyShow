package com.bookmyshow.theatre.exception;

public class ScreenValidationException extends RuntimeException {

    public ScreenValidationException(String message) {
        super(message);
    }

    public ScreenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}