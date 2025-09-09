package com.oocl.springbootdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createEmployees(@RequestBody Employee employee) {
        employee.setId(employees.size() + 1);
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

    static class Employee {
        private long id;
        private String name;
        private int age;
        private String gender;
        private double salary;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }
    }
}
