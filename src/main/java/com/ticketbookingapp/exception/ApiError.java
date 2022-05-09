package com.ticketbookingapp.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

class ApiError {
    private final HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final String exception;

    ApiError(HttpStatus status, Throwable ex) {
        this.status = status;
        this.message = ex.getMessage();
        this.exception = ex.getClass().getSimpleName();
    }

    ApiError(HttpStatus status, Throwable ex, String message) {
        this.status = status;
        this.message = message;
        this.exception = ex.getClass().getSimpleName();
    }

    HttpStatus getStatus() {
        return status;
    }
}
