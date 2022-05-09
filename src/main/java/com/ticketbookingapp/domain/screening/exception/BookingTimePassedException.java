package com.ticketbookingapp.domain.screening.exception;

public class BookingTimePassedException extends RuntimeException {
    public BookingTimePassedException(String message) {
        super(message);
    }
}
