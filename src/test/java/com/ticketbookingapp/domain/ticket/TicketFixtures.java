package com.ticketbookingapp.domain.ticket;

import com.ticketbookingapp.domain.ticket.dto.TicketDTO;

import java.math.BigDecimal;

public class TicketFixtures {
    public static Ticket ticket() {
        return new Ticket("student", new BigDecimal("18"));
    }

    public static TicketDTO ticketDTO() {
        return new TicketDTO("student", new BigDecimal("18"));
    }
}
