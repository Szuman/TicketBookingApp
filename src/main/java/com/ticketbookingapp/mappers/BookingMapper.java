package com.ticketbookingapp.mappers;

import com.ticketbookingapp.domain.booking.Booking;
import com.ticketbookingapp.domain.booking.dto.BookingDTO;
import com.ticketbookingapp.domain.booking.dto.ReservationDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    private BookingMapper() {
    }

    public static BookingDTO map(Booking booking) {
        List<ReservationDTO> reservations = booking.getReservations()
                .stream()
                .map(ReservationMapper::map)
                .collect(Collectors.toList());
        return new BookingDTO(
                booking.getId(),
                booking.getPersonName(),
                booking.getPersonSurname(),
                booking.getExpirationTime(),
                booking.getScreeningId(),
                reservations,
                booking.getTotalPrice());
    }

    public static List<BookingDTO> map(List<Booking> bookings) {
            return bookings
                    .stream()
                    .map(BookingMapper::map)
                    .collect(Collectors.toList());
        }
    }

