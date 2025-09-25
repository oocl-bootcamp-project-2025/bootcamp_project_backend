package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.controller.dto.ItineraryDTO;
import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.repository.ItineraryRepository;
import com.oocl.bootcampbackend.repository.dao.ItineraryDataJpaRepository;
import com.oocl.bootcampbackend.repository.dao.ItineraryJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ItineraryControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItineraryRepository itineraryRepository;
    @Autowired
    private ItineraryJpaRepository itineraryJpaRepository;
    @Autowired
    private ItineraryDataJpaRepository itineraryDataJpaRepository;

    Itinerary createItinerary(String title, String description, String phoneNumber, String startDate, int allNumber, String itineraryData) {
        ItineraryDTO itineraryDTO = new ItineraryDTO();
        itineraryDTO.setTitle(title);
        itineraryDTO.setDescription(description);
        itineraryDTO.setPhoneNumber(phoneNumber);
        itineraryDTO.setStartDate(startDate);
        itineraryDTO.setAllNumber(allNumber);
        itineraryDTO.setItineraryData(itineraryData);
        return itineraryRepository.save(itineraryDTO);
    }

    @BeforeEach
    void reset() {
        itineraryDataJpaRepository.deleteAll();
        itineraryJpaRepository.deleteAll();
    }

    @Test
    void should_get_itinerary_when_get_given_valid_phone_number() throws Exception {
        createItinerary("title1", "description1", "12345678", "9-25", 3, "itineraryData1");
        createItinerary("title2", "description2", "12345678", "9-28", 4, "itineraryData2");
        createItinerary("title3", "description3", "12345679", "9-28", 2, "itineraryData3");

        mockMvc.perform(get("/itineraries/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_get_itineraryData_when_get_given_valid_itineraryData_id() throws Exception {
        Itinerary itinerary = createItinerary("title1", "description1", "12345678", "9-25", 3, "itineraryData");

        mockMvc.perform(get("/itineraries/itineraryData/"+itinerary.getItineraryDataId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("itineraryData"));
    }
}
