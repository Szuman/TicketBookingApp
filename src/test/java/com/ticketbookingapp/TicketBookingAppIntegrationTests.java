package com.ticketbookingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketBookingAppIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturn201_whenPost_andReturnBooking_whenGet() throws Exception {
        //given
        String bodyJSON = "{\n" +
                "    \"name\": \"Łukasz\",\n" +
                "    \"surname\": \"Piszczek\",\n" +
                "    \"screeningId\": 1,\n" +
                "    \"reservations\": [\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 4\n" +
                "            },\n" +
                "            \"ticketType\": \"adult\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 5\n" +
                "            },\n" +
                "            \"ticketType\": \"student\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String bodyResponseJSON = "{\n" +
                "    \"id\": 1,\n" +
                "    \"personName\": \"Łukasz\",\n" +
                "    \"personSurname\": \"Piszczek\",\n" +
                "    \"expirationTime\": \"2222-02-22T20:07:02\",\n" +
                "    \"screeningId\": 1,\n" +
                "    \"reservations\": [\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": \"1\",\n" +
                "                \"seatNumber\": 4\n" +
                "            },\n" +
                "            \"ticketType\": \"adult\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": \"1\",\n" +
                "                \"seatNumber\": 5\n" +
                "            },\n" +
                "            \"ticketType\": \"student\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"totalPrice\": 43.00\n" +
                "}";

        //expect
        this.mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://*/bookings/1"));

        //and
        this.mockMvc.perform(get("/bookings/1")
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(bodyResponseJSON));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_whenNameIsTooShort() throws Exception {
        //given
        String bodyJSON = "{\n" +
                "    \"name\": \"Lu\",\n" +
                "    \"surname\": \"Piszczek\",\n" +
                "    \"screeningId\": 1,\n" +
                "    \"reservations\": [\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 4\n" +
                "            },\n" +
                "            \"ticketType\": \"adult\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 5\n" +
                "            },\n" +
                "            \"ticketType\": \"student\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String bodyResponseJSON = "{\n" +
                "    \"message\": \"Too short name value (minimum 3 characters)\",\n" +
                "    \"exception\": \"MethodArgumentNotValidException\"\n" +
                "}";

        //expect
        this.mockMvc.perform(post("/bookings")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(bodyResponseJSON));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_whenNameStartsWithLowerCase() throws Exception {
        //given
        String bodyJSON = "{\n" +
                "    \"name\": \"łukasz\",\n" +
                "    \"surname\": \"Piszczek\",\n" +
                "    \"screeningId\": 1,\n" +
                "    \"reservations\": [\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 4\n" +
                "            },\n" +
                "            \"ticketType\": \"adult\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 5\n" +
                "            },\n" +
                "            \"ticketType\": \"student\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String bodyResponseJSON = "{\n" +
                "    \"message\": \"Invalid pattern name value - Start with capital letter\",\n" +
                "    \"exception\": \"MethodArgumentNotValidException\"\n" +
                "}";


        //expect
        this.mockMvc.perform(post("/bookings")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(bodyResponseJSON));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_whenSurnameWithTwoPartsWithoutDash() throws Exception {
        //given
        String bodyJSON = "{\n" +
                "    \"name\": \"Marta\",\n" +
                "    \"surname\": \"Żmuda Trzebiatowska\",\n" +
                "    \"screeningId\": 1,\n" +
                "    \"reservations\": [\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 4\n" +
                "            },\n" +
                "            \"ticketType\": \"adult\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"seat\": {\n" +
                "                \"row\": 1,\n" +
                "                \"seatNumber\": 5\n" +
                "            },\n" +
                "            \"ticketType\": \"student\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    String bodyResponseJSON = "{\n" +
            "    \"message\": \"Invalid pattern surname value - Start with capital letter and divide two parts surname with dash (-)\",\n" +
            "    \"exception\": \"MethodArgumentNotValidException\"\n" +
            "}";
        //expect
        this.mockMvc.perform(post("/bookings")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(bodyResponseJSON));
    }

    @Test
    void shouldThrowMethodArgumentNotValidException_whenReservationIsEmpty() throws Exception {
        //given
        String bodyJSON = "{\n" +
                "    \"name\": \"Łukasz\",\n" +
                "    \"surname\": \"Piszczek\",\n" +
                "    \"screeningId\": 1,\n" +
                "    \"reservations\": []\n" +
                "}";
        String bodyResponseJSON = "{\n" +
                "    \"message\": \"There is no reservation\",\n" +
                "    \"exception\": \"MethodArgumentNotValidException\"\n" +
                "}";

        //expect
        this.mockMvc.perform(post("/bookings")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(bodyResponseJSON));
    }
}
