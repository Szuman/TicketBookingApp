package com.ticketbookingapp.mappers;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.screening.dto.ScreeningBasicInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ScreeningMapper {
    private ScreeningMapper() {
    }

    public static ScreeningBasicInfo map(Screening screening) {
        return new ScreeningBasicInfo(screening.getMovie().getTitle(),
                screening.getStartTime(), screening.getId());
    }

    public static List<ScreeningBasicInfo> map(List<Screening> screenings) {
        return screenings
                .stream()
                .map(ScreeningMapper::map)
                .collect(Collectors.toList());
    }
}
