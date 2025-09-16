package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.repository.dao.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepositoryDBImplementation implements TodoRepository {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    public List<Todo> query() {
        return todoJpaRepository.findAll();
    }

    public Todo findById(long id) {
        return todoJpaRepository.findById(id).orElse(null);
    }

    public Todo save(Todo todo) {
        todo.setDone(false);
        return todoJpaRepository.save(todo);
    }

//    public void clearAll() {
//        employeeJpaRepository.deleteAll();
//    }
//

//

//
//    public Employee update(Employee employee, UpdateEmployeeRequest updateEmployeeRequest) {
//        employee.setName(updateEmployeeRequest.getName());
//        employee.setSalary(updateEmployeeRequest.getSalary());
//        employee.setGender(updateEmployeeRequest.getGender());
//        employee.setAge(updateEmployeeRequest.getAge());
//        return employeeJpaRepository.save(employee);
//    }



//    public Employee delete(Employee target) {
//        target.setActiveStatus(false);
//        return employeeJpaRepository.save(target);
//    }
}
