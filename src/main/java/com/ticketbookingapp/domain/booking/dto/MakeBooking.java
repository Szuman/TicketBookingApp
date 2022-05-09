package com.ticketbookingapp.domain.booking.dto;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Value
public class MakeBooking {
    @Size(min = 3, message = "Too short name value (minimum 3 characters)")
    @Pattern(regexp = "^\\p{Lu}\\p{Ll}*$", message = "Invalid pattern name value - Start with capital letter")
    String name;

    @Size(min = 3, message = "Too short surname value (minimum 3 characters)")
    @Pattern(regexp = "(^\\p{Lu}\\p{Ll}*$)|(^\\p{Lu}\\p{Ll}{2,}-\\p{Lu}\\p{Ll}*$)",
            message = "Invalid pattern surname value - Start with capital letter and divide two parts surname with dash (-)")
    String surname;

    Long screeningId;

    @NotEmpty(message = "There is no reservation")
    List<ReservationDTO> reservations;
}
