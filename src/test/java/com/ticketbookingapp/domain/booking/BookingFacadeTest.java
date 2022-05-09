package com.ticketbookingapp.domain.booking;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.screening.ScreeningFacade;
import com.ticketbookingapp.domain.screening.ScreeningFixtures;
import com.ticketbookingapp.domain.booking.dto.BookingDTO;
import com.ticketbookingapp.domain.booking.dto.ReservationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookingFacadeTest {

    @Mock
    ReservationService reservationService;
    @Mock
    ScreeningFacade screeningFacade;
    @Mock
    BookingDAO bookingDao;

    BookingFacade bookingFacade;

    @BeforeEach
    void init() {
        bookingFacade = new BookingFacade(reservationService, screeningFacade, bookingDao);
    }

    @Test
    void shouldMakeBooking_whenReservationsArePrepared() {
        //given
        long id = 1L;
        Screening screening = ScreeningFixtures.screening(id);
        Mockito.when(screeningFacade.getScreeningIfStillAvailable(id)).thenReturn(screening);

        List<ReservationDTO> reservationDTOList = List.of(BookingFixtures.reservationDTO());
        List<Reservation> reservations = List.of(BookingFixtures.reservation());
        Mockito.when(reservationService.makeReservations(reservationDTOList, screening))
                .thenReturn(reservations);

        Mockito.when(bookingDao.save(BookingFixtures.booking())).thenReturn(id);

        //when
        Long actual = bookingFacade.makeBooking(BookingFixtures.makeBooking());

        //then
        assertThat(actual).isEqualTo(id);
    }

    @Test
    void shouldReturnBookingDTO_whenSpecificIdIsChosen() {
        //given
        Long id = 1L;
        Mockito.when(bookingDao.findById(id)).thenReturn(Optional.of(BookingFixtures.booking(1L)));
        BookingDTO expected = BookingFixtures.bookingDTO(id);

        //when
        BookingDTO actual = bookingFacade.getBooking(id);

        //then
        assertThat(actual).isEqualTo(expected);

    }
}
