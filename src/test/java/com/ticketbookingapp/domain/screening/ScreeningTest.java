package com.ticketbookingapp.domain.screening;

import com.ticketbookingapp.domain.screening.exception.BookingTimePassedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ScreeningTest {

    @Test
    void shouldThrowException_whenBookingTimePassed() {
        //given
        Screening screening = ScreeningFixtures.screening(1L);
        LocalDateTime time = LocalDateTime.of(2022, 5, 23, 13, 30);

        //expect
        assertThatThrownBy(() -> screening.validateBookingTimePassed(time, 120)).isInstanceOf(BookingTimePassedException.class)
                .hasMessage("Too late to book the screening!");
    }
}
