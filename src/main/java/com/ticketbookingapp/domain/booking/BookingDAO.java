package com.ticketbookingapp.domain.booking;

import java.util.Optional;

public interface BookingDAO {
    Long save(Booking booking);
    Optional<Booking> findById(Long id);
}
