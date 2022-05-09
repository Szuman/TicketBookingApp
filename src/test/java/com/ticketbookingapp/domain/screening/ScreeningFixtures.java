package com.ticketbookingapp.domain.screening;

import com.ticketbookingapp.domain.seat.SeatFixtures;
import com.ticketbookingapp.domain.ticket.TicketFixtures;
import com.ticketbookingapp.domain.screening.dto.ScreeningBasicInfo;
import com.ticketbookingapp.domain.screening.dto.ScreeningDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ScreeningFixtures {
    public static Screening screening(Long id) {
        LocalDateTime time = LocalDateTime.of(2022, 5, 8, 12, 5, 0);
        return new Screening(id, time, "1", new Movie(1L, "ABC", 123), List.of(SeatFixtures.seat()));
    }

    public static Screening screening(LocalDateTime time) {
        return new Screening(1L, time, "1", new Movie(1L, "ABC", 123), List.of(SeatFixtures.seat()));
    }

    public static ScreeningBasicInfo screeningBasicInfo(Long id) {
        LocalDateTime time1 = LocalDateTime.of(2022, 5, 8, 12, 5, 0);
        return new ScreeningBasicInfo("ABC", time1, id);
    }

    public static ScreeningDTO screeningDTO(Long id) {
        LocalDateTime time1 = LocalDateTime.of(2022, 5, 8, 12, 5, 0);
        return new ScreeningDTO(id, "ABC", time1, "1",
                List.of(SeatFixtures.seatDTO()), List.of(TicketFixtures.ticketDTO()));
    }
}
