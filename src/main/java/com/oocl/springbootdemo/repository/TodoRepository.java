package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.UpdateEmployeeRequest;

import java.util.List;

public interface TodoRepository {
    List<Todo> query();

    Todo save(Todo todo);

//    void clearAll();
//    Employee findById(long id);
//    Employee update(Employee employee, UpdateEmployeeRequest updateEmployeeRequest);
//    Employee delete(Employee target);
}
