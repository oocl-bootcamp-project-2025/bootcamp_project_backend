package com.oocl.springbootdemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.repository.CompanyRepository;
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
class CompanyControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void reset() {
        employeeRepository.clearAll();
        companyRepository.clearAll();
    }

    Company createCompany(String name) throws Exception {
        Company company = new Company();
        company.setName(name);
        return company;
    }

    Employee createEmployee(String name, int age, String gender, double salary, long company_id) throws Exception {
        Employee employee = new Employee();
        employee.setSalary(salary);
        employee.setGender(gender);
        employee.setName(name);
        employee.setAge(age);
        employee.setCompanyId(company_id);
        return employee;
    }

    @Test
    void should_create_company_when_post_given_a_valid_body() throws Exception {
        Company company = createCompany("oocl");

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("oocl"));
    }

    @Test
    void should_get_company_when_get_given_a_valid_id() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);

        mockMvc.perform(get("/companies/"+company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value("oocl"));
    }

    @Test
    void should_get_companies_when_get_given_null() throws Exception {

        for (int i=0; i<3; i++) {
            Company company = createCompany("oocl");
            companyRepository.save(company);
        }

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_update_company_when_put_given_a_valid_body() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);

        String updatedRequestBody = """
                        {
                            "name": "oocl Updated"
                        }
                """;

        mockMvc.perform(put("/companies/"+company.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value("oocl Updated"));
    }

    @Test
    void should_update_activeStatus_when_delete_given_a_valid_id() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        mockMvc.perform(delete("/companies/"+company.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_companies_when_get_given_page_and_size() throws Exception {
        Company company1 = createCompany("oocl");
        companyRepository.save(company1);
        Company company2 = createCompany("oocl");
        companyRepository.save(company2);
        Company company3 = createCompany("oocl");
        companyRepository.save(company3);

        mockMvc.perform(get("/companies?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company1.getId()))
                .andExpect(jsonPath("$[1].id").value(company2.getId()))
                .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/companies?page=2&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company3.getId()))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_get_null_when_get_given_a_invalid_id() throws Exception {
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_null_when_update_given_a_invalid_id() throws Exception {
        String updatedRequestBody = """
                        {
                            "name": "oocl Updated"
                        }
                """;

        mockMvc.perform(put("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNotFound());
    }
}
