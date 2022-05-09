package com.ticketbookingapp.domain.screening.exception;

public class ScreeningNotFoundException extends RuntimeException {
    public ScreeningNotFoundException(String message) {
        super(message);
    }
}
