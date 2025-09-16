package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.exception.TodoNotFoundException;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.UpdateTodoRequest;
import com.oocl.springbootdemo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> query() {
        return todoRepository.query();
    }

    public Todo get(long id) {
        Todo foundTodo = todoRepository.findById(id);
        if (foundTodo  == null) {
            throw new TodoNotFoundException();
        }
        return foundTodo;
    }

    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo update(long id, UpdateTodoRequest updateTodoRequest) {
        Todo foundTodo = get(id);
        return todoRepository.update(foundTodo, updateTodoRequest);
    }
//
//    public void delete(long id) {
//        Company foundCompany = get(id);
//        companyRepository.delete(foundCompany);
//    }
//
//    public void clear() {
//        companyRepository.clearAll();
//    }
}
