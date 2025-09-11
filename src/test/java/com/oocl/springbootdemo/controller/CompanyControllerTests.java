package com.oocl.springbootdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.repository.CompanyRepository;
import com.oocl.springbootdemo.service.CompanyService;
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
class CompanyControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void reset() {
        companyService.clear();
    }

    String requestBody = """
                {
                    "name": "OOCL"
                }
                """;

    long createCompanyReturnId(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString =mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }

    @Test
    void should_create_company_when_post_given_a_valid_body() throws Exception {
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("OOCL"));
    }

    @Test
    void should_get_company_when_get_given_a_valid_id() throws Exception {
        long createdId = createCompanyReturnId(requestBody);

        mockMvc.perform(get("/companies/"+createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(60000));
    }

    @Test
    void should_get_companies_when_get_given_null() throws Exception {

        for (int i=0; i<3; i++) {
            mockMvc.perform(post("/companies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody));
        }

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_update_company_when_put_given_a_valid_body() throws Exception {
        long createdId = createCompanyReturnId(requestBody);

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated",
                            "age": 300,
                            "gender": "Female",
                            "salary": 80000
                        }
                """;

        mockMvc.perform(put("/companies/"+createdId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.name").value("John Smith Updated"))
                .andExpect(jsonPath("$.age").value(300))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(80000));
    }

    @Test
    void should_update_activeStatus_when_delete_given_a_valid_id() throws Exception {
        long createdId = createCompanyReturnId(requestBody);

        mockMvc.perform(delete("/companies/"+createdId))
                .andExpect(jsonPath("$.activeStatus").value(false))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_companies_when_get_given_page_and_size() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith1",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        String requestBody2 = """
                {
                    "name": "John Smith2",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        String requestBody3 = """
                {
                    "name": "John Smith3",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        long createdId1 = createCompanyReturnId(requestBody1);
        long createdId2 = createCompanyReturnId(requestBody2);
        long createdId3 = createCompanyReturnId(requestBody3);

        mockMvc.perform(get("/companies?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(createdId1))
                .andExpect(jsonPath("$[1].id").value(createdId2))
                .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/companies?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(createdId3))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_get_null_when_get_given_a_invalid_id() throws Exception {
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        mockMvc.perform(get("/companies/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_null_when_update_given_a_invalid_id() throws Exception {
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated",
                            "age": 300,
                            "gender": "Female",
                            "salary": 80000
                        }
                """;

        mockMvc.perform(put("/companies/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_not_create_when_post_given_age_below_18() throws Exception {
        String requestBodyBelow18 = """
                {
                    "name": "John Smith",
                    "age": 17,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyBelow18))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_create_when_post_given_age_over_65() throws Exception {
        String requestBodyOver65 = """
                {
                    "name": "John Smith",
                    "age": 66,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyOver65))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_update_company_when_put_given_activeStatus_is_false() throws Exception {
        long createdId = createCompanyReturnId(requestBody);

        mockMvc.perform(delete("/companies/"+createdId));

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated",
                            "age": 300,
                            "gender": "Female",
                            "salary": 80000
                        }
                """;

        mockMvc.perform(put("/companies/"+createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isBadRequest());
    }
}
