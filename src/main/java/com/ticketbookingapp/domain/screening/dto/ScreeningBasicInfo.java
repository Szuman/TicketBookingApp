package com.ticketbookingapp.domain.screening.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ScreeningBasicInfo {
    String movieTitle;
    LocalDateTime dateTime;
    Long id;
}
