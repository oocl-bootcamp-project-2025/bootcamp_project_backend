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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void should_get_empty_json_when_get_given_storage_contains_no_todos() throws Exception {
        for (int i=0; i<10; i++) {
            createViewpoint("beijing"+i);
        }

        mockMvc.perform(get("/viewpoints/beijing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }
}
