package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.entity.Expert;
import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.repository.ViewpointRepository;
import com.oocl.bootcampbackend.repository.dao.AttractionJpaRepository;
import com.oocl.bootcampbackend.repository.dao.ExpertJpaRepository;
import com.oocl.bootcampbackend.repository.dao.ViewpointJpaRepository;
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
class ExpertControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ViewpointRepository viewpointRepository;
    @Autowired
    private AttractionJpaRepository attractionJpaRepository;
    @Autowired
    private ExpertJpaRepository expertJpaRepository;


    Attraction createAttraction(String location) {
        Attraction attraction = new Attraction();
        attraction.setName(location);
        return attractionJpaRepository.save(attraction);
    }

    Expert createExpert(String name, String location) {
        Expert expert = new Expert();
        expert.setName(name);
        expert.setLocation(location);
        return expertJpaRepository.save(expert);
    }

    @BeforeEach
    void reset() {
        attractionJpaRepository.deleteAll();
        expertJpaRepository.deleteAll();
    }

    @Test
    void should_get_experts_when_get_by_location_id_given_valid_attraction_and_experts() throws Exception {
        Attraction attraction = createAttraction("beijeng");
        createExpert("beijeng1", "beijeng");
        createExpert("beijeng2", "beijeng");

        mockMvc.perform(get("/experts/"+attraction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
