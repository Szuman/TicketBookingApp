package com.ticketbookingapp.domain.booking;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.ticket.Ticket;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String personName;

    @Column(nullable = false)
    private String personSurname;

    @ManyToOne
    @JoinColumn
    private Screening screening;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @OneToMany
    @JoinColumn
    private List<Reservation> reservations;

    Booking(String personName, String personSurname, Screening screening, List<Reservation> reservations) {
        this.personName = personName;
        this.personSurname = personSurname;
        this.screening = screening;
        this.reservations = reservations;
        setupExpirationTime(screening.getStartTime());
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        totalPrice = reservations.stream()
                .map(Reservation::getTicket)
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void setupExpirationTime(LocalDateTime screeningDateTime) {
        expirationTime = screeningDateTime.minus(15, ChronoUnit.MINUTES);
    }

    public Long getScreeningId() {
        return screening.getId();
    }
}
