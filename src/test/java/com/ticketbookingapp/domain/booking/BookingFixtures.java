package com.ticketbookingapp.domain.booking;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.screening.ScreeningFixtures;
import com.ticketbookingapp.domain.seat.Seat;
import com.ticketbookingapp.domain.seat.SeatFixtures;
import com.ticketbookingapp.domain.ticket.Ticket;
import com.ticketbookingapp.domain.booking.dto.BookingDTO;
import com.ticketbookingapp.domain.booking.dto.MakeBooking;
import com.ticketbookingapp.domain.booking.dto.ReservationDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingFixtures {
    public static Booking booking(Long id) {
        Screening screening = ScreeningFixtures.screening(1L);
        LocalDateTime time = screening.getStartTime().minus(15, ChronoUnit.MINUTES);
        return new Booking(id, "Scott", "Pilgrim", ScreeningFixtures.screening(1L), time, new BigDecimal("18"),
                List.of(reservation()));
    }

    public static Booking booking() {
        return new Booking("Scott", "Pilgrim", ScreeningFixtures.screening(1L), List.of(reservation()));
    }

    public static MakeBooking makeBooking() {
        return new MakeBooking("Scott", "Pilgrim", 1L, List.of(reservationDTO()));
    }

    public static BookingDTO bookingDTO(Long id) {
        LocalDateTime time = LocalDateTime.of(2022, 5, 8, 11, 50, 0);
        return new BookingDTO(id, "Scott", "Pilgrim", time,
                1L, List.of(reservationDTO()), new BigDecimal("18"));
    }

    public static Reservation reservation() {
        return new Reservation(new Seat("A",1), new Ticket("student", new BigDecimal("18")));
    }

    public static ReservationDTO reservationDTO() {
        return new ReservationDTO(SeatFixtures.seatDTO(), "student");
    }
}
