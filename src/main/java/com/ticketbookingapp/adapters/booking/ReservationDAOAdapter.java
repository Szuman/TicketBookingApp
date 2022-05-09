package com.ticketbookingapp.adapters.booking;

import com.ticketbookingapp.domain.booking.Reservation;
import com.ticketbookingapp.domain.booking.ReservationDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class ReservationDAOAdapter implements ReservationDAO {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> saveAll(List<Reservation> reservations) {
        return (List<Reservation>) reservationRepository.saveAll(reservations);
    }
}
