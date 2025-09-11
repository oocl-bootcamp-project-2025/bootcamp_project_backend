package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Employee;

import java.util.List;

public interface EmployeeRepository {
    void clearAll();

    Employee save(Employee employee);

    Employee findById(long id);

    Employee update(Employee target, Employee updateEmployee);

    List<Employee> query(String gender, Integer page, Integer size);

    Employee delete(Employee target);

    boolean hasDuplicatedNameAndGender(Employee target);
}
