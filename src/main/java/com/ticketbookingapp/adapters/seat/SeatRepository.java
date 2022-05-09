package com.ticketbookingapp.adapters.seat;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.seat.Seat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

interface SeatRepository extends CrudRepository<Seat, Long> {
    Optional<Seat> findBySeatRowAndSeatNumberAndScreening(String seatRow, int seatNumber, Screening screening);
    List<Seat> findAllByScreeningAndAvailable(Screening screening, boolean available);
    List<Seat> findBySeatRowAndScreeningOrderBySeatNumber(String seatRow, Screening screening);
}
