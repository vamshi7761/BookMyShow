package com.bookmyshow.theatre.exception;

public class SeatTypeValidationException extends RuntimeException {

    public SeatTypeValidationException(String message) {
        super(message);
    }

    public SeatTypeValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
