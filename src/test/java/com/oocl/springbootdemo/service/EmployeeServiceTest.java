package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.exception.*;
import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.UpdateEmployeeRequest;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee createdEmployee = employeeService.create(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void should_not_create_when_post_given_age_below_18() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(17);
        employee.setGender("Male");
        employee.setSalary(3000.0);

        assertThrows(EmployeeNotAmongLegalAgeException.class,
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

        assertThrows(EmployeeNotAmongLegalAgeException.class,
                () -> employeeService.create(employee)
        );
    }

    @Test
    void should_not_create_when_post_given_age_over_30_and_salary_below_20000() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(31);
        employee.setGender("Male");
        employee.setSalary(300.0);

        assertThrows(EmployeeNotHavingAcceptablePaidException.class,
                () -> employeeService.create(employee)
        );
    }

    @Test
    void should_not_create_when_post_given_duplicated_name_and_gender() {
        Employee employee = new Employee();
        employee.setName("tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(300.0);

        when(employeeRepository.hasDuplicatedNameAndGender(employee)).thenReturn(true);

        assertThrows(EmployeeNameAndGenderDuplicatedException.class,
                () -> employeeService.create(employee)
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

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.get(10)
        );
    }

    @Test
    void should_return_employee_list_when_get_given_gender() {
        when(employeeRepository.query("Male", null, null)).thenReturn(List.of(new Employee(), new Employee()));
        List<Employee> result = employeeService.query("Male", null, null);

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).query("Male", null, null);
    }

    @Test
    void should_return_employee_list_when_get_given_page_and_size() {
        when(employeeRepository.query(null, 0, 2)).thenReturn(List.of(new Employee(), new Employee()));
        List<Employee> result = employeeService.query(null, 0, 2);

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).query(null, 0, 2);
    }

    @Test
    void should_update_employee_when_update_given_activeStatus_is_true() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(300000.0);
        employee.setActiveStatus(true);

        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest();
        updateEmployeeRequest.setName("tom updated");
        updateEmployeeRequest.setAge(20);
        updateEmployeeRequest.setGender("Male");
        updateEmployeeRequest.setSalary(300000.0);

        when(employeeRepository.findById(1)).thenReturn(employee);
        when(employeeRepository.update(employee, updateEmployeeRequest)).thenReturn(employee);

        Employee result = employeeService.update(1, updateEmployeeRequest);
        assertEquals("tom", result.getName());
        verify(employeeRepository, times(1)).update(any(), any());
    }

    @Test
    void should_update_employee_when_update_given_activeStatus_is_false() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("tom");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setSalary(300000.0);
        employee.setActiveStatus(false);

        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest();
        updateEmployeeRequest.setName("tom updated");
        updateEmployeeRequest.setAge(20);
        updateEmployeeRequest.setGender("Male");
        updateEmployeeRequest.setSalary(300000.0);

        when(employeeRepository.findById(1)).thenReturn(employee);
        when(employeeRepository.update(employee, updateEmployeeRequest)).thenReturn(employee);

        assertThrows(EmployeeNotActiveException.class,
                () -> employeeService.update(1, updateEmployeeRequest)
        );
        verify(employeeRepository, never()).update(any(), any());
    }
}