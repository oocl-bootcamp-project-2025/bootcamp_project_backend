package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.EmployeeNotAmoungLegalAgeException;
import com.oocl.springbootdemo.EmployeeNotHavingAcceptablePaidException;
import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void should_create_when_post_given_a_valid_body() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(18);
        employee.setGender("Male");
        employee.setSalary(3000.0);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void should_not_create_when_post_given_age_below_18() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(17);
        employee.setGender("Male");
        employee.setSalary(3000.0);

        assertThrows(EmployeeNotAmoungLegalAgeException.class,
                () -> employeeService.create(employee)
        );
    }

    @Test
    void should_not_create_when_post_given_age_over_65() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(66);
        employee.setGender("Male");
        employee.setSalary(300000.0);

        assertThrows(EmployeeNotAmoungLegalAgeException.class, () ->
                employeeService.create(employee)
        );
    }

    @Test
    void should_not_create_when_post_given_age_over_30_and_salary_below_20000() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(31);
        employee.setGender("Male");
        employee.setSalary(300.0);

        assertThrows(EmployeeNotHavingAcceptablePaidException.class, () ->
                employeeService.create(employee)
        );
    }

    @Test
    void should_return_employee_when_get_given_exist_employee_id() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(300000.0);

        when(employeeRepository.findById(1)).thenReturn(employee);

        Employee foundEmployee = employeeService.get(1);
        assertEquals(employee, foundEmployee);
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void should_return_employee_when_get_given_not_exist_employee_id() {
        when(employeeRepository.findById(10)).thenReturn(null);

        Employee foundEmployee = employeeService.get(10);
        assertNull(foundEmployee);
        verify(employeeRepository, times(1)).findById(10);
    }


}