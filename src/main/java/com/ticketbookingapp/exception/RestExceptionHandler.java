package com.ticketbookingapp.exception;

import com.ticketbookingapp.domain.booking.exception.BookingNotFoundException;
import com.ticketbookingapp.domain.screening.exception.BookingTimePassedException;
import com.ticketbookingapp.domain.screening.exception.ScreeningNotFoundException;
import com.ticketbookingapp.domain.seat.exception.IllegalFreeSpaceBetweenTakenSeatsException;
import com.ticketbookingapp.domain.seat.exception.SeatAlreadyTakenException;
import com.ticketbookingapp.domain.seat.exception.SeatNotFoundException;
import com.ticketbookingapp.domain.ticket.exception.TicketNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String defaultMessage = ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex, defaultMessage);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BookingTimePassedException.class)
    ResponseEntity<Object> handleBookingTimePassedException(BookingTimePassedException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalFreeSpaceBetweenTakenSeatsException.class)
    ResponseEntity<Object> handleNoFreeSpaceBetweenTakenSeatsException(IllegalFreeSpaceBetweenTakenSeatsException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(SeatAlreadyTakenException.class)
    ResponseEntity<Object> handleSeatAlreadyTakenException(SeatAlreadyTakenException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    ResponseEntity<Object> handleBookingNotFoundException(BookingNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ScreeningNotFoundException.class)
    ResponseEntity<Object> handleScreeningNotFoundException(ScreeningNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(SeatNotFoundException.class)
    ResponseEntity<Object> handleSeatNotFoundException(SeatNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
