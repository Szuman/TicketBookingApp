package com.ticketbookingapp.domain.seat.exception;

public class IllegalFreeSpaceBetweenTakenSeatsException extends RuntimeException {
    public IllegalFreeSpaceBetweenTakenSeatsException(String message) {
        super(message);
    }
}
