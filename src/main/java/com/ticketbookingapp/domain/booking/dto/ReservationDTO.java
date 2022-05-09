package com.ticketbookingapp.domain.booking.dto;

import com.ticketbookingapp.domain.seat.dto.SeatDTO;
import lombok.Value;

@Value
public class ReservationDTO {
    SeatDTO seat;
    String ticketType;
}
