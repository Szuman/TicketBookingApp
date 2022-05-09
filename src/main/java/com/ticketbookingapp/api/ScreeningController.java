package com.ticketbookingapp.api;

import com.ticketbookingapp.domain.screening.ScreeningFacade;
import com.ticketbookingapp.domain.screening.dto.ScreeningBasicInfo;
import com.ticketbookingapp.domain.screening.dto.ScreeningDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("screenings")
class ScreeningController {

    private final ScreeningFacade screeningFacade;

    @GetMapping
    List<ScreeningBasicInfo> getScreeningsByDate(@RequestParam(value = "from", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime from,
                                                 @RequestParam(value = "to", required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime to,
                                                 @PageableDefault(sort = {"movie.title", "startTime"})
                                                 Pageable pageable) {
        return screeningFacade.findScreeningsByDateTimeInterval(from, to, pageable);
    }

    @GetMapping("/{id}")
    ScreeningDTO getScreeningDetails(@PathVariable Long id) {
        return screeningFacade.getScreening(id);
    }
}
