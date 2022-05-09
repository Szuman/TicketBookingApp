package com.ticketbookingapp.api;

import com.ticketbookingapp.domain.booking.BookingFacade;
import com.ticketbookingapp.domain.booking.dto.BookingDTO;
import com.ticketbookingapp.domain.booking.dto.MakeBooking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("bookings")
class BookingController {
    private final BookingFacade bookingFacade;

    @PostMapping
    ResponseEntity<Void> makeBooking(@Valid @RequestBody MakeBooking makeBooking) {
        Long bookingId = bookingFacade.makeBooking(makeBooking);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookingId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    BookingDTO getBooking(@PathVariable Long id) {
        return bookingFacade.getBooking(id);
    }
}
