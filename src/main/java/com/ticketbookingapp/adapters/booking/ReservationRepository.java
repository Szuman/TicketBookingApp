package com.ticketbookingapp.adapters.booking;

import com.ticketbookingapp.domain.booking.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAll();
}
