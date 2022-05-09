package com.ticketbookingapp.domain.booking;

import com.ticketbookingapp.domain.booking.dto.BookingDTO;
import com.ticketbookingapp.domain.booking.dto.MakeBooking;
import com.ticketbookingapp.domain.booking.dto.ReservationDTO;
import com.ticketbookingapp.domain.booking.exception.BookingNotFoundException;
import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.screening.ScreeningFacade;
import com.ticketbookingapp.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingFacade {

    private final ReservationService reservationService;
    private final ScreeningFacade screeningFacade;
    private final BookingDAO bookingDao;

    @Transactional
    public Long makeBooking(MakeBooking makeBooking) {
        Screening screening = screeningFacade.getScreeningIfStillAvailable(makeBooking.getScreeningId());
        List<ReservationDTO> reservationDTOs = makeBooking.getReservations();
        List<Reservation> reservations = reservationService.makeReservations(reservationDTOs, screening);
        return bookingDao.save(new Booking(makeBooking.getName(), makeBooking.getSurname(), screening, reservations));
    }

    public BookingDTO getBooking(Long id) {
        Booking booking = bookingDao.findById(id).orElseThrow(() ->
                new BookingNotFoundException("Booking (id=" + id +") does not exist"));
        return BookingMapper.map(booking);
    }
}