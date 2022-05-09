package com.ticketbookingapp.domain.seat;

import com.ticketbookingapp.domain.screening.Screening;

import java.util.List;
import java.util.Optional;

public interface SeatDAO {
    Optional<Seat> findSeat(String row, int seatNumber, Screening screening);
    List<Seat> findAllByScreeningAndAvailability(Screening screening, boolean available);
    List<Seat> findByRowAndScreeningOrderedBySeatNumber(String row, Screening screening);
    void saveAll(List<Seat> seats);
}
