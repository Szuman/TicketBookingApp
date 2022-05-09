package com.ticketbookingapp.domain.seat;

import com.ticketbookingapp.domain.screening.ScreeningFixtures;
import com.ticketbookingapp.domain.seat.dto.SeatDTO;
import com.ticketbookingapp.domain.seat.exception.IllegalFreeSpaceBetweenTakenSeatsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class SeatFacadeTest {

    private final int forbiddenNumberOfFreeSeatsBetweenTaken = 1;

    @Mock
    SeatDAO seatDao;

    SeatFacade seatFacade;

    @BeforeEach
    void init() {
        seatFacade = new SeatFacade(seatDao, forbiddenNumberOfFreeSeatsBetweenTaken);
    }

    @Test
    public void shouldReturnSeat_whenRowSeatNumberAndScreeningIsProvided() {
        //given
        Long id = 1L;
        Mockito.when(seatDao.findSeat("A", 1, ScreeningFixtures.screening(id)))
                .thenReturn(Optional.of(SeatFixtures.seat()));
        Seat expected = SeatFixtures.seat();

        //when
        Seat actual = seatFacade.getSeat("A", 1, ScreeningFixtures.screening(id));

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnSeatList_whenScreeningIsProvided() {
        //given
        Long id = 1L;
        Mockito.when(seatDao.findAllByScreeningAndAvailability(ScreeningFixtures.screening(id), true))
                .thenReturn(List.of(SeatFixtures.seat()));
        List<SeatDTO> expected = List.of(SeatFixtures.seatDTO());

        //when
        List<SeatDTO> actual = seatFacade.findAllAvailableSeatsByScreening(ScreeningFixtures.screening(id));

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldTakeSeats_whenSeatsAreProvided() {
        //given
        Long id = 1L;
        List<SeatDTO> seatDTOList = List.of(SeatFixtures.seatDTO());
        Mockito.when(seatDao.findByRowAndScreeningOrderedBySeatNumber("A", ScreeningFixtures.screening(id)))
                .thenReturn(SeatFixtures.seatsInRow(10, List.of(2, 3, 4)));

        //when
        seatFacade.takeSeats(seatDTOList, ScreeningFixtures.screening(id));

        //then
        Mockito.verify(seatDao).saveAll(List.of(SeatFixtures.seatAllArgs()));
    }

    @Test
    public void shouldThrowIllegalSpaceBetweenTakenSeatsException_whenProvidedSeatsLeftIllegalSpaceBetweenTaken() {
        //given
        Long id = 1L;
        List<SeatDTO> seatDTOList = List.of(SeatFixtures.seatDTO());
        Mockito.when(seatDao.findByRowAndScreeningOrderedBySeatNumber("A", ScreeningFixtures.screening(id)))
                .thenReturn(SeatFixtures.seatsInRow(10, List.of(3, 4)));

        //expect
        assertThatThrownBy(() -> seatFacade.takeSeats(seatDTOList, ScreeningFixtures.screening(id)))
                .isInstanceOf(IllegalFreeSpaceBetweenTakenSeatsException.class)
                .hasMessage("There cannot be " + forbiddenNumberOfFreeSeatsBetweenTaken + " place(s) left between taken seats");

    }
}
