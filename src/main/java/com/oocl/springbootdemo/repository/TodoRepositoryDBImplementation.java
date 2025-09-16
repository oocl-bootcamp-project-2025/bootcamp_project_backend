package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.UpdateTodoRequest;
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

    public Todo update(Todo todo, UpdateTodoRequest updateTodoRequest) {
        todo.setText(updateTodoRequest.getText());
        todo.setDone(updateTodoRequest.isDone());
        return todoJpaRepository.save(todo);
    }

    public void clearAll() {
        todoJpaRepository.deleteAll();
    }


//    public Employee delete(Employee target) {
//        target.setActiveStatus(false);
//        return employeeJpaRepository.save(target);
//    }
}
