package com.oocl.springbootdemo.service;

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

    public Map<String, Long> create(Employee employee) {
        employeeRepository.save(employee);
        return Map.of("id", employee.getId());
    }

    public Employee update(long id, Employee updateEmployee) {
        Employee target = employeeRepository.findById(id);
        if (target != null) {
           return employeeRepository.update(target, updateEmployee);
        }
        return target;
    }

    public Employee get(long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> query(String gender, Integer page, Integer size) {
        return employeeRepository.query(gender, page, size);
    }

    public void delete(long id) {
        employeeRepository.delete(id);
    }
}
