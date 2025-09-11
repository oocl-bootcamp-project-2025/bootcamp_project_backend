package com.oocl.springbootdemo.object;

import com.oocl.springbootdemo.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CompanyTests {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void resetId() {
        CompanyRepository.clear();
    }

    @Test
    void should_create_company_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith"
                }
                """;

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void should_get_company_when_get_given_a_valid_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith"
                }
                """;

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody));

        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"));
    }

    @Test
    void should_get_companies_when_get_given_null() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith"
                }
                """;

        for (int i=0; i<10; i++) {
            mockMvc.perform(post("/companies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody));
        }

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    void should_update_company_when_put_given_a_valid_body() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith"
                        }
                """;

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated"
                        }
                """;

        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith Updated"));
    }

    @Test
    void should_update_activeStatus_when_delete_given_a_valid_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith"
                }
                """;

        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(delete("/companies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_companies_when_get_given_page_and_size() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith1"
                }
                """;
        for (int i=0; i<10; i++) {
            mockMvc.perform(post("/companies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody));
        }

        mockMvc.perform(get("/companies?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_get_null_when_get_given_a_invalid_id() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith"
                        }
                """;

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        mockMvc.perform(get("/companies/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_null_when_update_given_a_invalid_id() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith"
                        }
                """;

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated"
                        }
                """;

        mockMvc.perform(put("/companies/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNotFound());
    }
}
