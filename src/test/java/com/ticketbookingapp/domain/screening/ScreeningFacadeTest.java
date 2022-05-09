package com.ticketbookingapp.domain.screening;

import com.ticketbookingapp.domain.screening.dto.ScreeningBasicInfo;
import com.ticketbookingapp.domain.screening.dto.ScreeningDTO;
import com.ticketbookingapp.domain.seat.SeatFacade;
import com.ticketbookingapp.domain.seat.SeatFixtures;
import com.ticketbookingapp.domain.ticket.TicketFacade;
import com.ticketbookingapp.domain.ticket.TicketFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ScreeningFacadeTest {

    int bookingTimesUpMinutes = 15;

    @Mock
    ScreeningDAO screeningDao;
    @Mock
    SeatFacade seatFacade;
    @Mock
    TicketFacade ticketFacade;

    ScreeningFacade screeningFacade;

    @BeforeEach
    void init() {
        screeningFacade = new ScreeningFacade(bookingTimesUpMinutes, screeningDao, seatFacade, ticketFacade);
    }

    @Test
    void shouldReturnScreeningInfo_whenTimeIntervalProvided() {
        //given
        Long id = 1L;
        LocalDateTime from = LocalDateTime.of(2022, 5, 4, 17, 15, 0);
        LocalDateTime to = LocalDateTime.of(2022, 5, 4, 17, 45, 0);
        Mockito.when(screeningDao.findAllByDateTimeBetween(from, to, Pageable.unpaged())).thenReturn(List.of(ScreeningFixtures.screening(id)));
        List<ScreeningBasicInfo> expected = List.of(ScreeningFixtures.screeningBasicInfo(id));

        //when
        List<ScreeningBasicInfo> actual = screeningFacade.findScreeningsByDateTimeInterval(from, to, Pageable.unpaged());

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnScreeningDTO_whenIdIsProvided() {
        //given
        Long id = 1L;
        Screening screening = ScreeningFixtures.screening(id);
        Mockito.when(seatFacade.findAllAvailableSeatsByScreening(screening)).thenReturn(List.of(SeatFixtures.seatDTO()));
        Mockito.when(ticketFacade.findAll()).thenReturn(List.of(TicketFixtures.ticketDTO()));
        Mockito.when(screeningDao.findById(id)).thenReturn(Optional.of(screening));
        ScreeningDTO expected = ScreeningFixtures.screeningDTO(id);

        //when
        ScreeningDTO actual = screeningFacade.getScreening(id);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnScreening_whenIdIsProvided() {
        //given
        LocalDateTime now = LocalDateTime.now();
        int minutesOverLimit = bookingTimesUpMinutes + 30;
        Screening expected = ScreeningFixtures.screening(now.plusMinutes(minutesOverLimit));
        Mockito.when(screeningDao.findById(1L))
                .thenReturn(Optional.of(ScreeningFixtures.screening(now.plusMinutes(minutesOverLimit))));

        //when
        Screening actual = screeningFacade.getScreeningIfStillAvailable(1L);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
