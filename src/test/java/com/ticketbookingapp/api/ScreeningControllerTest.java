package com.ticketbookingapp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbookingapp.domain.screening.ScreeningFacade;
import com.ticketbookingapp.domain.screening.ScreeningFixtures;
import com.ticketbookingapp.domain.screening.dto.ScreeningBasicInfo;
import com.ticketbookingapp.domain.screening.dto.ScreeningDTO;
import com.ticketbookingapp.domain.screening.exception.ScreeningNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ScreeningController.class)
class ScreeningControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ScreeningFacade screeningFacade;

    @Test
    void shouldReturn200AndScreeningList_whenSendGetWithDateTimeInterval() throws Exception {
        //given
        ScreeningBasicInfo screening = ScreeningFixtures.screeningBasicInfo(1L);
        LocalDateTime from = LocalDateTime.of(2022, 5, 4, 13, 30);
        LocalDateTime to = LocalDateTime.of(2022, 5, 4, 19, 30);
        String fromStr = from.format(DateTimeFormatter.ISO_DATE_TIME);
        String toStr = to.format(DateTimeFormatter.ISO_DATE_TIME);
        Mockito.when(screeningFacade.findScreeningsByDateTimeInterval(eq(from), eq(to), any(Pageable.class))).thenReturn(List.of(screening));

        //expect
        this.mockMvc.perform(get("/screenings?from=" + fromStr + "&to=" + toStr))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + objectMapper.writeValueAsString(screening) + "]"));
        Mockito.verify(screeningFacade).findScreeningsByDateTimeInterval(eq(from), eq(to), any(Pageable.class));
    }

    @Test
    void shouldReturn200AndScreeningList_whenSendGetWithDateTime() throws Exception {
        //given
        ScreeningBasicInfo screening = ScreeningFixtures.screeningBasicInfo(1L);
        LocalDateTime from = LocalDateTime.of(2022, 5, 4, 13, 30);
        String fromStr = from.format(DateTimeFormatter.ISO_DATE_TIME);
        Mockito.when(screeningFacade.findScreeningsByDateTimeInterval(eq(from), isNull(), any(Pageable.class))).thenReturn(List.of(screening));

        //expect
        this.mockMvc.perform(get("/screenings?from=" + fromStr))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + objectMapper.writeValueAsString(screening) + "]"));
        Mockito.verify(screeningFacade).findScreeningsByDateTimeInterval(eq(from), isNull(), any(Pageable.class));
    }

    @Test
    void shouldReturn200AndScreening_whenSendGetWithId() throws Exception {
        //given
        Long id = 1L;
        ScreeningDTO screeningDTO = ScreeningFixtures.screeningDTO(id);
        Mockito.when(screeningFacade.getScreening(id)).thenReturn(screeningDTO);

        //expect
        this.mockMvc.perform(get("/screenings/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(screeningDTO)));
        Mockito.verify(screeningFacade).getScreening(id);
    }

    @Test
    void shouldReturn404AndScreeningNotFound_whenSendGetWithWrongId() throws Exception {
        //given
        Long id = 1L;
        Mockito.when(screeningFacade.getScreening(id)).thenThrow(new ScreeningNotFoundException("Screening Not Found"));

        //expect
        this.mockMvc.perform(get("/screenings/" + id))
                .andExpect(status().isNotFound());
        Mockito.verify(screeningFacade).getScreening(id);
    }

}
