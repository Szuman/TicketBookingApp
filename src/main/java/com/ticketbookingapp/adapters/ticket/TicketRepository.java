package com.ticketbookingapp.adapters.ticket;

import com.ticketbookingapp.domain.ticket.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAll();
    Optional<Ticket> findByType(String type);
}