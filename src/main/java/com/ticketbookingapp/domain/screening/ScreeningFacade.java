package com.ticketbookingapp.domain.screening;

import com.ticketbookingapp.domain.screening.dto.ScreeningBasicInfo;
import com.ticketbookingapp.domain.screening.dto.ScreeningDTO;
import com.ticketbookingapp.domain.screening.exception.ScreeningNotFoundException;
import com.ticketbookingapp.domain.seat.SeatFacade;
import com.ticketbookingapp.domain.seat.dto.SeatDTO;
import com.ticketbookingapp.domain.ticket.TicketFacade;
import com.ticketbookingapp.domain.ticket.dto.TicketDTO;
import com.ticketbookingapp.mappers.ScreeningMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningFacade {

    private final int bookingTimesUpMinutes;
    private final ScreeningDAO screeningDao;
    private final SeatFacade seatFacade;
    private final TicketFacade ticketFacade;

    public ScreeningFacade(@Value("${booking.timesup.minutes:15}") int bookingTimesUpMinutes, ScreeningDAO screeningDao, SeatFacade seatFacade, TicketFacade ticketFacade) {
        this.bookingTimesUpMinutes = bookingTimesUpMinutes;
        this.screeningDao = screeningDao;
        this.seatFacade = seatFacade;
        this.ticketFacade = ticketFacade;
    }

    public List<ScreeningBasicInfo> findScreeningsByDateTimeInterval(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return ScreeningMapper.map(screeningDao.findAllByDateTimeBetween(from, to, pageable));
    }

    public ScreeningDTO getScreening(Long id) {
        Screening screening = screeningDao.findById(id).orElseThrow(() ->
                new ScreeningNotFoundException("Screening (id=" + id + ") does not exist"));
        List<SeatDTO> availableSeats = seatFacade.findAllAvailableSeatsByScreening(screening);
        List<TicketDTO> tickets = ticketFacade.findAll();
        return new ScreeningDTO(screening.getId(), screening.getMovieTitle(), screening.getStartTime(),
                screening.getScreeningRoom(), availableSeats, tickets);
    }

    public Screening getScreeningIfStillAvailable(Long id) {
        Screening screening = screeningDao.findById(id).orElseThrow(() ->
                new ScreeningNotFoundException("Screening (id=" + id + ") does not exist"));
        screening.validateBookingTimePassed(LocalDateTime.now(), bookingTimesUpMinutes);
        return screening;
    }
}
