package com.ticketbookingapp.domain.seat;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.seat.exception.SeatAlreadyTakenException;
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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "screening")
@EqualsAndHashCode
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatRow;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn
    private Screening screening;

    public Seat(String seatRow, int seatNumber) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    public Seat(String seatRow, int seatNumber, Screening screening) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.screening = screening;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    Seat take() {
        if (!available) {
            throw new SeatAlreadyTakenException("Chosen seat - row: " + seatRow + " seatNumber: " + seatNumber + " is already taken");
        }
        this.available = false;
        return this;
    }
}
