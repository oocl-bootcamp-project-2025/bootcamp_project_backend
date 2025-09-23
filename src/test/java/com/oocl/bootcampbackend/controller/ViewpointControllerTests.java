package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.repository.ViewpointRepository;
import com.oocl.bootcampbackend.repository.dao.ViewpointJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ViewpointControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ViewpointRepository viewpointRepository;
    @Autowired
    private ViewpointJpaRepository viewpointJpaRepository;


    void createViewpoint(String area) {
        Viewpoint viewpoint = new Viewpoint();
        viewpoint.setArea(area);
        viewpointJpaRepository.save(viewpoint);
    }

    @BeforeEach
    void reset() {
        viewpointJpaRepository.deleteAll();
    }

    @Test
    void should_get_areas_when_get_area_given_incompleted_area_name() throws Exception {
        for (int i=0; i<10; i++) {
            createViewpoint("beijing"+i);
        }

        mockMvc.perform(get("/viewpoints/bei"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0]").value("beijing0"));
    }

    @Test
    void should_get_areas_when_get_all_area_given_storage_with_duplicated_area() throws Exception {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                createViewpoint("beijing"+i);
            }
        }

        mockMvc.perform(get("/viewpoints/areas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[1]").value("beijing1"));
    }
}
