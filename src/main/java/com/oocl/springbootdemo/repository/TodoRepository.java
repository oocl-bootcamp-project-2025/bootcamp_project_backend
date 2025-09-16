package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.UpdateTodoRequest;

import java.util.List;

public interface TodoRepository {
    List<Todo> query();

    Todo save(Todo todo);

    Todo findById(long id);

    Todo update(Todo todo, UpdateTodoRequest updateTodoRequest);

    void delete(Todo todo);

    void clearAll();
}
