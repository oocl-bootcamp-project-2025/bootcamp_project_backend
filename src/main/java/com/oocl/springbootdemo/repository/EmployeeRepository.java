package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.UpdateEmployeeRequest;

import java.util.List;

public interface EmployeeRepository {
    void clearAll();

    Employee save(Employee employee);

    Employee findById(long id);

    Employee update(Employee employee, UpdateEmployeeRequest updateEmployeeRequest);

    List<Employee> query(String gender, Integer page, Integer size);

    Employee delete(Employee target);

    boolean hasDuplicatedNameAndGender(Employee target);
}
