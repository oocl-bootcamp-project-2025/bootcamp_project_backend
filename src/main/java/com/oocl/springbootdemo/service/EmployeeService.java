package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final static List<Employee> employees = new ArrayList<>();
    private static long id = 0;

    public static void clear() {
        employees.clear();
        id = 0;
    }

    public Map<String, Long> create(Employee employee) {
        employee.setId(++id); //variable
        employees.add(employee);
        return Map.of("id", employee.getId());
    }

    public Employee update(long id, Employee updateEmployee) {
        Employee target = employees.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst()
                .orElse(null);

        if (target != null) {
            target.setName(updateEmployee.getName());
            target.setAge(updateEmployee.getAge());
            target.setGender(updateEmployee.getGender());
            target.setSalary(updateEmployee.getSalary());
        }

        return target;
    }

    public Employee get(long id) {
        return employees.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null);
    }

    public List<Employee> query(String gender, Integer page, Integer size) {
        if (gender != null) {
            return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
        };
        if (page != null && size != null) {
            return employees.subList(page*size, page*size+size);
        }
        return employees;
    }

    public void delete(long id) {
        employees.remove(employees.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null));
    }
}
