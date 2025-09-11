package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.UpdateEmployeeRequest;
import com.oocl.springbootdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<Employee> createEmployees(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> queryEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.query(gender, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.update(id, updateEmployeeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployees(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(employeeService.delete(id));
    }
}
