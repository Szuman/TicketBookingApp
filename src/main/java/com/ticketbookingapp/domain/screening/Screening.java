package com.ticketbookingapp.domain.screening;


import com.ticketbookingapp.domain.screening.exception.BookingTimePassedException;
import com.ticketbookingapp.domain.seat.Seat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private String screeningRoom;

    @ManyToOne
    @JoinColumn
    private Movie movie;

    @OneToMany(targetEntity = Seat.class, mappedBy = "screening", cascade = CascadeType.ALL)
    private List<Seat> seats;

    String getMovieTitle() {
        return movie.getTitle();
    }

    void validateBookingTimePassed(LocalDateTime validateDateTime, int bookingTimesUpMinutes) {
        LocalDateTime lastCall = startTime.minus(bookingTimesUpMinutes, ChronoUnit.MINUTES);
        if (validateDateTime.isAfter(lastCall)) {
             throw new BookingTimePassedException("Too late to book the screening!");
         }
    }

}
