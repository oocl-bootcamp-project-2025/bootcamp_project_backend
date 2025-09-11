package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryDBImplementation {
    private final static List<Employee> employees = new ArrayList<>();
    private static long id = 0;

    public static void clear() {
        employees.clear();
        id = 0;
    }

    public Employee save(Employee employee) {
        employee.setId(++id);
        employee.setActiveStatus(true);
        employees.add(employee);
        return employee;
    }

    public Employee findById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Employee update(Employee target, Employee updateEmployee) {
        target.setName(updateEmployee.getName());
        target.setAge(updateEmployee.getAge());
        target.setGender(updateEmployee.getGender());
        target.setSalary(updateEmployee.getSalary());
        return target;
    }

    public List<Employee> query(String gender, Integer page, Integer size) {
        if (gender != null) {
            return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
        }
        if (page != null && size != null) {
            return employees.subList((page - 1) * size, (page - 1) * size + size);
        }
        return employees;
    }

    public Employee delete(Employee target) {
        target.setActiveStatus(false);
        return target;
    }

    public boolean hasDuplicatedNameAndGender(Employee target) {
        return employees.stream()
                .anyMatch(employee -> employee.getName().equals(target.getName()) && employee.getGender().equals(target.getGender()));
    }
}
