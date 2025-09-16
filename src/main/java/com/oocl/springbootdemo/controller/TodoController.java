package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.object.Todo;
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
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.create(todo));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Company> updateCompanies(@PathVariable long id, @RequestBody UpdateCompanyRequest updateCompanyRequest) {
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(companyService.update(id, updateCompanyRequest));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Company> deleteCompanies(@PathVariable long id) {
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
}
