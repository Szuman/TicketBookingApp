package com.ticketbookingapp.domain.ticket.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class TicketDTO {
    String name;
    BigDecimal price;
}
