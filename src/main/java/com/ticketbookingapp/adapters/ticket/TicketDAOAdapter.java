package com.ticketbookingapp.adapters.ticket;

import com.ticketbookingapp.domain.ticket.Ticket;
import com.ticketbookingapp.domain.ticket.TicketDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class TicketDAOAdapter implements TicketDAO {
    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findByName(String name) {
        return ticketRepository.findByType(name);
    }
}
