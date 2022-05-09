package com.ticketbookingapp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbookingapp.domain.booking.BookingFacade;
import com.ticketbookingapp.domain.booking.BookingFixtures;
import com.ticketbookingapp.domain.booking.dto.BookingDTO;
import com.ticketbookingapp.domain.booking.dto.MakeBooking;
import com.ticketbookingapp.domain.booking.exception.BookingNotFoundException;
import com.ticketbookingapp.domain.screening.exception.BookingTimePassedException;
import com.ticketbookingapp.domain.seat.exception.IllegalFreeSpaceBetweenTakenSeatsException;
import com.ticketbookingapp.domain.seat.exception.SeatAlreadyTakenException;
import com.ticketbookingapp.domain.seat.exception.SeatNotFoundException;
import com.ticketbookingapp.domain.ticket.exception.TicketNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookingFacade bookingFacade;

    @Test
    void shouldReturn201AndRedirectionToCreatedBooking_whenSendPost() throws Exception {
        //given
        Long id = 1L;
        MakeBooking makeBooking = BookingFixtures.makeBooking();
        Mockito.when(bookingFacade.makeBooking(makeBooking)).thenReturn(id);

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeBooking)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://*/bookings/" + id));
        Mockito.verify(bookingFacade).makeBooking(makeBooking);
    }

    @Test
    void shouldReturn200AndBooking_whenSendGetWithId() throws Exception {
        //given
        Long id = 1L;
        BookingDTO bookingDTO = BookingFixtures.bookingDTO(id);
        Mockito.when(bookingFacade.getBooking(id)).thenReturn(bookingDTO);

        //expect
        this.mockMvc.perform(get("/bookings/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bookingDTO)));
        Mockito.verify(bookingFacade).getBooking(id);
    }

    @Test
    void shouldReturn400AndThrowBookingTimePassedException_whenSendPostWithScreeningInThePast() throws Exception {
        //given
        Mockito.when(bookingFacade.makeBooking(BookingFixtures.makeBooking())).thenThrow(new BookingTimePassedException("It is Too Late"));

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BookingFixtures.makeBooking())))
                .andExpect(status().isBadRequest());
        Mockito.verify(bookingFacade).makeBooking(BookingFixtures.makeBooking());
    }

    @Test
    void shouldReturn403AndThrowIllegalSpaceBetweenTakenSeatsException_whenSendPostWithIllegalSpacingChoice() throws Exception {
        //given
        Mockito.when(bookingFacade.makeBooking(BookingFixtures.makeBooking()))
                .thenThrow(new IllegalFreeSpaceBetweenTakenSeatsException("Illegal number of free seat(s) between taken"));

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BookingFixtures.makeBooking())))
                .andExpect(status().isForbidden());
        Mockito.verify(bookingFacade).makeBooking(BookingFixtures.makeBooking());
    }

    @Test
    void shouldReturn404AndThrowSeatNotFoundException_whenSendPostWithNotExistingSeat() throws Exception {
        //given
        Mockito.when(bookingFacade.makeBooking(BookingFixtures.makeBooking())).thenThrow(new SeatNotFoundException("Seat Not Found"));

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BookingFixtures.makeBooking())))
                .andExpect(status().isNotFound());
        Mockito.verify(bookingFacade).makeBooking(BookingFixtures.makeBooking());
    }

    @Test
    void shouldReturn404AndThrowTicketNotFoundException_whenSendPostWithNotExistingTicket() throws Exception {
        //given
        Mockito.when(bookingFacade.makeBooking(BookingFixtures.makeBooking())).thenThrow(new TicketNotFoundException("Ticket Not Found"));

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BookingFixtures.makeBooking())))
                .andExpect(status().isNotFound());
        Mockito.verify(bookingFacade).makeBooking(BookingFixtures.makeBooking());
    }

    @Test
    void shouldReturn404AndBookingNotFound_whenSendGetWithWrongId() throws Exception {
        //given
        Long id = 1L;
        Mockito.when(bookingFacade.getBooking(id)).thenThrow(new BookingNotFoundException("Booking Not Found"));

        //expect
        this.mockMvc.perform(get("/bookings/" + id))
                .andExpect(status().isNotFound());
        Mockito.verify(bookingFacade).getBooking(id);
    }

    @Test
    void shouldReturn409AndThrowSeatAlreadyTakenException_whenSendPostWithAlreadyTakenSeat() throws Exception {
        //given
        Mockito.when(bookingFacade.makeBooking(BookingFixtures.makeBooking())).thenThrow(new SeatAlreadyTakenException("Already Taken"));

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BookingFixtures.makeBooking())))
                .andExpect(status().isConflict());
        Mockito.verify(bookingFacade).makeBooking(BookingFixtures.makeBooking());
    }
}
