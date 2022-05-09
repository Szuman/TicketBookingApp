package com.ticketbookingapp.domain.booking;

import java.util.List;

public interface ReservationDAO {
    List<Reservation> findAll();
    List<Reservation> saveAll(List<Reservation> reservations);
}
