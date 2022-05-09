package com.ticketbookingapp.domain.seat;

import com.ticketbookingapp.domain.screening.ScreeningFixtures;
import com.ticketbookingapp.domain.seat.dto.SeatDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SeatFixtures {
    public static Seat seat() {
        return new Seat("A", 1);
    }

    public static Seat seatAllArgs() {
        return new Seat(1L, "A", 1, false, ScreeningFixtures.screening(1L));
    }

    public static SeatDTO seatDTO() {
        return new SeatDTO("A", 1);
    }

    public static List<Seat> seatsInRow(int numberOfSeatsInRow, List<Integer> seatNumbersTaken) {
        return IntStream.rangeClosed(1, numberOfSeatsInRow)
                .mapToObj(i -> new Seat(Long.parseLong(i + ""), "A", i, true, ScreeningFixtures.screening(1L)))
                .map(seat -> {
                    if (seatNumbersTaken.contains(seat.getSeatNumber())) {
                        return seat.take();
                    }
                    return seat;
                })
                .collect(Collectors.toList());
    }
}
