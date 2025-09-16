package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.exception.TodoIncompletePayloadException;
import com.oocl.springbootdemo.exception.TodoNotFoundException;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.TodoRequest;
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

    public Todo create(TodoRequest todoRequest) {
        if (todoRequest.getText() == null || todoRequest.getText().isEmpty()) {
            throw new TodoIncompletePayloadException();
        }
        return todoRepository.save(todoRequest);
    }

    public Todo update(long id, TodoRequest todoRequest) {
        if (todoRequest.getText() == null || todoRequest.getText().isEmpty()) {
            throw new TodoIncompletePayloadException();
        }
        Todo foundTodo = get(id);
        return todoRepository.update(foundTodo, todoRequest);
    }

    public void delete(long id) {
        Todo foundTodo = get(id);
        todoRepository.delete(foundTodo);
    }
}
