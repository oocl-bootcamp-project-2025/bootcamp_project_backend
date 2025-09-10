package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createEmployees(@RequestBody Employee employee) {
        Map<String, Long> result = employeeService.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployees(@PathVariable long id, @RequestBody Employee updateEmployee) {
        Employee result = employeeService.update(id, updateEmployee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
        Employee result = employeeService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> queryEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        List<Employee> result = employeeService.query(gender, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> deleteEmployees(@PathVariable long id) {
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
