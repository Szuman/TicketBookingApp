package com.ticketbookingapp.domain.booking.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class BookingDTO {
    Long id;
    String personName;
    String personSurname;
    LocalDateTime expirationTime;
    Long screeningId;
    List<ReservationDTO> reservations;
    BigDecimal totalPrice;
}
