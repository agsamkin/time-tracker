package com.example.timetracker.exception;

public class TrackTimeException extends RuntimeException {
    public TrackTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
