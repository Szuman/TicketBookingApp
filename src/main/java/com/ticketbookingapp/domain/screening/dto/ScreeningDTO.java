package com.ticketbookingapp.domain.screening.dto;

import com.ticketbookingapp.domain.seat.dto.SeatDTO;
import com.ticketbookingapp.domain.ticket.dto.TicketDTO;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ScreeningDTO {
    Long id;
    String movieTitle;
    LocalDateTime dateTime;
    String screeningRoom;
    List<SeatDTO> availableSeats;
    List<TicketDTO> tickets;
}
