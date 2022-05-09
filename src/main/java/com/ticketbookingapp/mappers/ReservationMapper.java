package com.ticketbookingapp.mappers;

import com.ticketbookingapp.domain.booking.Reservation;
import com.ticketbookingapp.domain.booking.dto.ReservationDTO;

public class ReservationMapper {
    private ReservationMapper() {
    }

    public static ReservationDTO map(Reservation reservation) {
        return new ReservationDTO(SeatMapper.map(reservation.getSeat()), reservation.getTicketType());
    }
}
