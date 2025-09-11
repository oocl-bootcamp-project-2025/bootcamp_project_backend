package com.oocl.springbootdemo.object;

import com.oocl.springbootdemo.repository.EmployeeRepository;
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
class EmployeeTests {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void resetId() {
        EmployeeRepository.clear();
    }

    @Test
    void should_create_employee_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.activeStatus").value(true));
    }

    @Test
    void should_get_employee_when_get_given_a_valid_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(60000));
    }

    @Test
    void should_get_male_employees_when_get_given_filter_male() throws Exception {
        String requestBodyMale1 = """
                {
                    "name": "John Smith1",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        String requestBodyFemale = """
                {
                    "name": "Lily",
                    "age": 20,
                    "gender": "Female",
                    "salary": 8000
                }
                """;

        String requestBodyMale2 = """
                {
                    "name": "John Smith2",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyMale1));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyFemale));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMale2));

        mockMvc.perform(get("/employees?gender=Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_get_employees_when_get_given_null() throws Exception {
        String requestBodyMale1 = """
                {
                    "name": "John Smith1",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        String requestBodyMale2 = """
                {
                    "name": "John Smith2",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        String requestBodyFemale = """
                {
                    "name": "Lily",
                    "age": 20,
                    "gender": "Female",
                    "salary": 8000
                }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMale1));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyFemale));

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMale2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_update_employee_when_put_given_a_valid_body() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith",
                            "age": 30,
                            "gender": "Male",
                            "salary": 60000
                        }
                """;

        mockMvc.perform(post("/employees")
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

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith Updated"))
                .andExpect(jsonPath("$.age").value(300))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(80000));
    }

    @Test
    void should_update_activeStatus_when_delete_given_a_valid_id() throws Exception {
        String requestBody = """
                {
                    "name": "John Smith",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(delete("/employees/1"))
                .andExpect(jsonPath("$.activeStatus").value(false))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_employees_when_get_given_page_and_size() throws Exception {
        String requestBody1 = """
                {
                    "name": "John Smith1",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1));

        String requestBody2 = """
                {
                    "name": "John Smith2",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2));

        String requestBody3 = """
                {
                    "name": "John Smith3",
                    "age": 30,
                    "gender": "Male",
                    "salary": 60000
                }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody3));

        mockMvc.perform(get("/employees?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_get_null_when_get_given_a_invalid_id() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith",
                            "age": 30,
                            "gender": "Male",
                            "salary": 60000
                        }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        mockMvc.perform(get("/employees/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_null_when_update_given_a_invalid_id() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith",
                            "age": 30,
                            "gender": "Male",
                            "salary": 60000
                        }
                """;

        mockMvc.perform(post("/employees")
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

        mockMvc.perform(put("/employees/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_not_create_when_post_given_age_below_18() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith",
                            "age": 17,
                            "gender": "Male",
                            "salary": 60000
                        }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_create_when_post_given_age_over_65() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith",
                            "age": 66,
                            "gender": "Male",
                            "salary": 60000
                        }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_update_employee_when_put_given_activeStatus_is_false() throws Exception {
        String requestBody = """
                        {
                            "name": "John Smith",
                            "age": 30,
                            "gender": "Male",
                            "salary": 60000
                        }
                """;

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        mockMvc.perform(delete("/employees/1"));

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated",
                            "age": 300,
                            "gender": "Female",
                            "salary": 80000
                        }
                """;

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isBadRequest());
    }
}
