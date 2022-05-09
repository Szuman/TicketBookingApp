package com.ticketbookingapp.domain.ticket;

import com.ticketbookingapp.domain.ticket.dto.TicketDTO;
import com.ticketbookingapp.domain.ticket.exception.TicketNotFoundException;
import com.ticketbookingapp.mappers.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketFacade {
    private final TicketDAO ticketDao;

    public List<TicketDTO> findAll() {
        return TicketMapper.map(ticketDao.findAll());
    }

    public Ticket findByName(String name) {
        return ticketDao.findByName(name).orElseThrow(() ->
                new TicketNotFoundException("Chosen ticket type (" + name +") does not exist"));
    }
}
