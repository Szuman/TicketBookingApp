package com.ticketbookingapp.adapters.booking;

import com.ticketbookingapp.domain.booking.Booking;
import com.ticketbookingapp.domain.booking.BookingDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookingDAOAdapter implements BookingDAO {

    private final BookingRepository bookingRepository;

    @Override
    public Long save(Booking booking) {
        return bookingRepository.save(booking).getId();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }
}
