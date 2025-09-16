package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.UpdateEmployeeRequest;

import java.util.List;

public interface TodoRepository {
//    void clearAll();

//    Employee save(Employee employee);
//
//    Employee findById(long id);
//
//    Employee update(Employee employee, UpdateEmployeeRequest updateEmployeeRequest);

    List<Todo> query();

//    Employee delete(Employee target);
}
