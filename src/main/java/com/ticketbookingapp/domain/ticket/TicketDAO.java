package com.ticketbookingapp.domain.ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDAO {
    List<Ticket> findAll();
    Optional<Ticket> findByName(String name);
}
