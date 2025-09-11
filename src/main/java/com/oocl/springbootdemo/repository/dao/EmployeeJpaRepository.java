package com.oocl.springbootdemo.repository.dao;

import com.oocl.springbootdemo.object.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByGender(String gender);
}
