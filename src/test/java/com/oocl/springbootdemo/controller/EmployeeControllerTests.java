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
class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void reset() {
        employeeRepository.clearAll();
        companyRepository.clearAll();
    }

    Company createCompany(String name) throws Exception {
        Company company = new Company();
        company.setName("oocl");
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
    void should_create_employee_when_post_given_a_valid_body() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 20, "Male", 60000, company.getId());

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.activeStatus").value(true));
    }

    @Test
    void should_get_employee_when_get_given_a_valid_id() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(60000));
    }


    @Test
    void should_get_male_employees_when_get_given_filter_male() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee1 = createEmployee("John Smith1", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee1);
        Employee employee2 = createEmployee("John Smith2", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee2);
        Employee employee3 = createEmployee("John Smith3", 20, "Female", 60000, company.getId());
        employeeRepository.save(employee3);

        mockMvc.perform(get("/employees?gender=Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_get_employees_when_get_given_null() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee1 = createEmployee("John Smith1", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee1);
        Employee employee2 = createEmployee("John Smith2", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee2);
        Employee employee3 = createEmployee("John Smith3", 20, "Female", 60000, company.getId());
        employeeRepository.save(employee3);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_update_employee_when_put_given_a_valid_body() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee);

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated",
                            "age": 300,
                            "gender": "Female",
                            "salary": 80000
                        }
                """;

        mockMvc.perform(put("/employees/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value("John Smith Updated"))
                .andExpect(jsonPath("$.age").value(300))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(80000));
    }

    @Test
    void should_get_null_when_get_given_a_invalid_id() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update_activeStatus_when_delete_given_a_valid_id() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(jsonPath("$.activeStatus").value(false))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_employees_when_get_given_page_and_size() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee1 = createEmployee("John Smith1", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee1);
        Employee employee2 = createEmployee("John Smith2", 20, "Male", 60000, company.getId());
        employeeRepository.save(employee2);
        Employee employee3 = createEmployee("John Smith3", 20, "Female", 60000, company.getId());
        employeeRepository.save(employee3);

        mockMvc.perform(get("/employees?page=1&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()))
                .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get("/employees?page=2&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee3.getId()))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_get_null_when_update_given_a_invalid_id() throws Exception {
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
                .andExpect(status().isNotFound());
    }

    @Test
    void should_not_create_when_post_given_age_below_18() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 17, "Male", 60000, company.getId());

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_create_when_post_given_age_over_65() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 66, "Male", 60000, company.getId());

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_update_employee_when_put_given_activeStatus_is_false() throws Exception {
        Company company = createCompany("oocl");
        companyRepository.save(company);
        Employee employee = createEmployee("John Smith", 66, "Male", 60000, company.getId());
        employeeRepository.save(employee);
        employeeRepository.delete(employee);

        String updatedRequestBody = """
                        {
                            "name": "John Smith Updated",
                            "age": 300,
                            "gender": "Female",
                            "salary": 80000
                        }
                """;

        mockMvc.perform(put("/employees/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isBadRequest());
    }
}
