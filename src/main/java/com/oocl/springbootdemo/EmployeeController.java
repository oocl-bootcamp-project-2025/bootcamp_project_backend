package com.oocl.springbootdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>();
    private static long id = 0;

    public static void setId(long id) {
        EmployeeController.id = id;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createEmployees(@RequestBody Employee employee) {
        employee.setId(++id); //variable
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", employee.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployees(@RequestBody Employee updateEmployee, @PathVariable long id) {
        Employee target = employees.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst()
                .orElse(null);

        if (target != null) {
            target.setName(updateEmployee.getName());
            target.setAge(updateEmployee.getAge());
            target.setGender(updateEmployee.getGender());
            target.setSalary(updateEmployee.getSalary());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(target);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employees.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null));
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> queryEmployees(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (gender != null) {
            return ResponseEntity.status(HttpStatus.OK).body(employees.stream().filter(employee -> employee.getGender().equals(gender)).toList());
        };
        if (page != null && size != null) {
            return ResponseEntity.status(HttpStatus.OK).body(employees.subList(page*size, page*size+size));
        }
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> deleteEmployees(@PathVariable long id) {
        employees.remove(employees.stream().filter(employee -> employee.getId()==id).findFirst().orElse(null));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
