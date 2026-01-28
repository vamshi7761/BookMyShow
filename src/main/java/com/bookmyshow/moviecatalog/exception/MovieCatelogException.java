package com.bookmyshow.moviecatalog.exception;

public class MovieCatelogException extends RuntimeException {

    public MovieCatelogException(String message) {
        super(message);
    }

    public MovieCatelogException(String message, Throwable cause) {
        super(message, cause);
    }
}