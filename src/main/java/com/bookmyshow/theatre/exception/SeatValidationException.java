package com.bookmyshow.theatre.exception;

public class SeatValidationException extends RuntimeException {

    public SeatValidationException(String message) {
        super(message);
    }

    public SeatValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}