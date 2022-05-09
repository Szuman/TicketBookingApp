package com.ticketbookingapp.mappers;

import com.ticketbookingapp.domain.ticket.Ticket;
import com.ticketbookingapp.domain.ticket.dto.TicketDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TicketMapper {
    private TicketMapper() {
    }

    public static TicketDTO map(Ticket ticket) {
        return new TicketDTO(ticket.getType(), ticket.getPrice());
    }

    public static Ticket map(TicketDTO ticketDTO) {
        return new Ticket(ticketDTO.getName(), ticketDTO.getPrice());
    }

    public static List<TicketDTO> map(List<Ticket> tickets) {
        return tickets
                .stream()
                .map(TicketMapper::map)
                .collect(Collectors.toList());
    }
}
