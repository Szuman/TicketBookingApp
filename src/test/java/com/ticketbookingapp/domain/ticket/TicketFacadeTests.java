package com.ticketbookingapp.domain.ticket;

import com.ticketbookingapp.domain.ticket.dto.TicketDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TicketFacadeTests {

    @InjectMocks
    TicketFacade ticketFacade;

    @Mock
    TicketDAO ticketDao;

    @Test
    public void shouldReturnTicketDTOList_forAllTickets() {
        //given
        Mockito.when(ticketDao.findAll())
                .thenReturn(List.of(TicketFixtures.ticket()));
        List<TicketDTO> expected = List.of(TicketFixtures.ticketDTO());

        //when
        List<TicketDTO> actual = ticketFacade.findAll();

        //then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldReturnTicket_whenTypeNameIsProvided() {
        //given
        Mockito.when(ticketDao.findByName("student"))
                .thenReturn(Optional.of(TicketFixtures.ticket()));
        Ticket expected = TicketFixtures.ticket();

        //when
        Ticket actual = ticketFacade.findByName("student");

        //then
        assertThat(actual).isEqualTo(expected);

    }
}
