package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.TodoRequest;

import java.util.List;

public interface TodoRepository {
    List<Todo> query();

    Todo save(TodoRequest todoRequest);

    Todo findById(long id);

    Todo update(Todo todo, TodoRequest todoRequest);

    void delete(Todo todo);

    void clearAll();
}
