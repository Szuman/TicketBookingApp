package com.ticketbookingapp.mappers;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.seat.Seat;
import com.ticketbookingapp.domain.seat.dto.SeatDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SeatMapper {
    private SeatMapper() {
    }

    public static SeatDTO map(Seat seat) {
    return new SeatDTO(seat.getSeatRow(), seat.getSeatNumber());
    }

    public static Seat map(SeatDTO seatDTO, Screening screening) {
        return new Seat(seatDTO.getRow(), seatDTO.getSeatNumber(), screening);
    }

    public static List<SeatDTO> map(List<Seat> seats) {
        return seats
                .stream()
                .map(SeatMapper::map)
                .collect(Collectors.toList());
    }

    public static List<Seat> map(List<SeatDTO> seatDTOList, Screening screening) {
        return seatDTOList
                .stream()
                .map(seatDTO -> SeatMapper.map(seatDTO, screening))
                .collect(Collectors.toList());
    }
}
