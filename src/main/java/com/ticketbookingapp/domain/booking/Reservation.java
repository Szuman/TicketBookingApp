package com.ticketbookingapp.domain.booking;


import com.ticketbookingapp.domain.seat.Seat;
import com.ticketbookingapp.domain.ticket.Ticket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Seat seat;

    @OneToOne
    private Ticket ticket;
    
    Reservation(Seat seat, Ticket ticket) {
        this.seat = seat;
        this.ticket = ticket;
    }

    public String getTicketType() {
        return ticket.getType();
    }
}


