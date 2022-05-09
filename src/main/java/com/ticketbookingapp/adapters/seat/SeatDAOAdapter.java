package com.ticketbookingapp.adapters.seat;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.seat.Seat;
import com.ticketbookingapp.domain.seat.SeatDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class SeatDAOAdapter implements SeatDAO {
    private final SeatRepository seatRepository;

    @Override
    public Optional<Seat> findSeat(String row, int seatNumber, Screening screening) {
        return seatRepository.findBySeatRowAndSeatNumberAndScreening(row, seatNumber, screening);
    }

    @Override
    public List<Seat> findAllByScreeningAndAvailability(Screening screening, boolean available) {
        return seatRepository.findAllByScreeningAndAvailable(screening, available);
    }

    @Override
    public List<Seat> findByRowAndScreeningOrderedBySeatNumber(String row, Screening screening) {
        return seatRepository.findBySeatRowAndScreeningOrderBySeatNumber(row, screening);
    }

    @Override
    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }
}
