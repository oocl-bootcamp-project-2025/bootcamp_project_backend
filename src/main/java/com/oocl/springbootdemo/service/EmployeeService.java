package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.EmployeeNotAmoungLegalAgeException;
import com.oocl.springbootdemo.EmployeeNotFoundException;
import com.oocl.springbootdemo.EmployeeNotHavingAcceptablePaidException;
import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new EmployeeNotAmoungLegalAgeException();
        }
        if (employee.getAge() >= 30 && employee.getSalary() < 20000) {
            throw new EmployeeNotHavingAcceptablePaidException();
        }
        return employeeRepository.save(employee);
    }

    public Employee get(long id) {
        Employee foundEmployee = employeeRepository.findById(id);
        if (foundEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        return foundEmployee;
    }

    public List<Employee> query(String gender, Integer page, Integer size) {
        return employeeRepository.query(gender, page, size);
    }

    public Employee update(long id, Employee updateEmployee) {
        Employee foundEmployee = get(id);
        return employeeRepository.update(foundEmployee, updateEmployee);
    }

    public Employee delete(long id) {
        Employee foundEmployee = get(id);
        return employeeRepository.delete(foundEmployee);
    }
}
