package com.ticketbookingapp.domain.seat;

import com.ticketbookingapp.domain.screening.ScreeningFixtures;
import com.ticketbookingapp.domain.seat.exception.SeatAlreadyTakenException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SeatTest {

    @Test
    void shouldThrowSeatAlreadyTakenException_whenTryingToTakeSeatAlreadyTaken() {
        //given
        Seat seat = new Seat("A", 1, ScreeningFixtures.screening(1L));

        //expect
        assertThatThrownBy(seat::take).isInstanceOf(SeatAlreadyTakenException.class)
                .hasMessage("Chosen seat - row: A seatNumber: 1 is already taken");
    }
}
