package com.ticketbookingapp.domain.seat;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.seat.dto.SeatDTO;
import com.ticketbookingapp.domain.seat.exception.IllegalFreeSpaceBetweenTakenSeatsException;
import com.ticketbookingapp.domain.seat.exception.SeatAlreadyTakenException;
import com.ticketbookingapp.domain.seat.exception.SeatNotFoundException;
import com.ticketbookingapp.mappers.SeatMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeatFacade {
    private final SeatDAO seatDao;
    private final int forbiddenNumberOfFreeSeatsBetweenTaken;

    public SeatFacade(SeatDAO seatDao, @Value("${seats.forbidden.spaceBetweenTaken:1}") int forbiddenNumberOfFreeSeatsBetweenTaken) {
        this.seatDao = seatDao;
        this.forbiddenNumberOfFreeSeatsBetweenTaken = forbiddenNumberOfFreeSeatsBetweenTaken;
    }

    public Seat getSeat(String row, int seatNumber, Screening screening) {
        return seatDao.findSeat(row, seatNumber,screening).orElseThrow(() -> new SeatNotFoundException("Seat Not Found"));
    }

    public List<SeatDTO> findAllAvailableSeatsByScreening(Screening screening) {
        return SeatMapper.map(seatDao.findAllByScreeningAndAvailability(screening, true));
    }

    public void takeSeats(List<SeatDTO> reservationSeats, Screening screening) {
        Map<String, List<Integer>> chosenSeatNumbersByRow = reservationSeats.stream()
                .collect(Collectors.groupingBy(SeatDTO::getRow, Collectors.mapping(SeatDTO::getSeatNumber, Collectors.toList())));
        List<Seat> takenSeats = new ArrayList<>();
        for (String row: chosenSeatNumbersByRow.keySet()) {
            List<Integer> seatNumbersInRowToTake = chosenSeatNumbersByRow.get(row);
            validateIfReservationContainsOnlyDistinctSeatsInRow(seatNumbersInRowToTake);

            List<Seat> allSeatsInRowOrderedBySeatNumber = findAllSeatsInRowOrderedBySeatNumber(row, screening);
            List<Integer> allSeatNumbersInRow = getSeatNumbers(allSeatsInRowOrderedBySeatNumber);
            validateIfSeatsToTakeExist(seatNumbersInRowToTake, allSeatNumbersInRow);

            List<Seat> validatedTakenSeatsInRow = getValidatedTakenSeatsInRow(seatNumbersInRowToTake, allSeatsInRowOrderedBySeatNumber);
            takenSeats.addAll(validatedTakenSeatsInRow);
        }
        saveAll(takenSeats);
    }

    private void validateIfReservationContainsOnlyDistinctSeatsInRow(List<Integer> seatNumbers) {
        Set<Integer> distinctSeatNumbers = new HashSet<>(seatNumbers);
        if (distinctSeatNumbers.size() != seatNumbers.size()) {
            throw new SeatAlreadyTakenException("One seat in reservation was typed more than once");
        }
    }

    private void validateIfSeatsToTakeExist(Collection<Integer> seatNumbersInRowToTake, Collection<Integer> allSeatNumbersInRow) {
        if (!allSeatNumbersInRow.containsAll(seatNumbersInRowToTake)) {
            throw new SeatNotFoundException("Chosen seat does not exist");
        }
    }

    private List<Integer> getSeatNumbers(List<Seat> seats) {
        return seats
                .stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());
    }

    private List<Seat> getValidatedTakenSeatsInRow(List<Integer> seatNumbersInRowToTake, List<Seat> seatsInRowOrderedBySeatNumber) {
        int numberOfFreeSeatsBetweenTaken = 0;
        List<Seat> takenSeats = new ArrayList<>();
        for (Seat seat: seatsInRowOrderedBySeatNumber) {
            if (seatNumbersInRowToTake.contains(seat.getSeatNumber())) {
                takenSeats.add(seat.take());
            }
            if (seat.isAvailable()) {
                numberOfFreeSeatsBetweenTaken++;
            } else if (numberOfFreeSeatsBetweenTaken == forbiddenNumberOfFreeSeatsBetweenTaken) {
                throw new IllegalFreeSpaceBetweenTakenSeatsException("There cannot be " + forbiddenNumberOfFreeSeatsBetweenTaken + " place(s) left between taken seats");
            } else {
                numberOfFreeSeatsBetweenTaken = 0;
            }
        }
        return takenSeats;
    }

    private List<Seat> findAllSeatsInRowOrderedBySeatNumber(String row, Screening screening) {
        return seatDao.findByRowAndScreeningOrderedBySeatNumber(row, screening);
    }

    private void saveAll(List<Seat> seats) {
        seatDao.saveAll(seats);
    }
}
