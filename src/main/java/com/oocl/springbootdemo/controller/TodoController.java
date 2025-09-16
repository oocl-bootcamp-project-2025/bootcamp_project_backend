package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.TodoRequest;
import com.oocl.springbootdemo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<Todo>> queryTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.query());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Todo> createTodo(@RequestBody TodoRequest todoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.create(todoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable long id, @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todoService.update(id, todoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable long id) {
        todoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
