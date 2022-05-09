package com.ticketbookingapp.domain.booking;

import com.ticketbookingapp.domain.booking.dto.ReservationDTO;
import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.seat.SeatFacade;
import com.ticketbookingapp.domain.seat.dto.SeatDTO;
import com.ticketbookingapp.domain.ticket.TicketFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ReservationService {

    private final ReservationDAO reservationDao;
    private final SeatFacade seatFacade;
    private final TicketFacade ticketFacade;

    List<Reservation> makeReservations(List<ReservationDTO> reservationDTOs, Screening screening) {
        seatFacade.takeSeats(reservationDTOs.stream().map(ReservationDTO::getSeat).collect(Collectors.toList()), screening);
        List<Reservation> reservations = reservationDTOs
                .stream()
                .map(reservationDTO -> {
                            SeatDTO seatDTO = reservationDTO.getSeat();
                            String ticketType = reservationDTO.getTicketType();
                            return new Reservation(
                                    seatFacade.getSeat(seatDTO.getRow(), seatDTO.getSeatNumber(), screening),
                                    ticketFacade.findByName(ticketType)
                            );
                        }
                ).collect(Collectors.toList());
        return reservationDao.saveAll(reservations);
    }
}
