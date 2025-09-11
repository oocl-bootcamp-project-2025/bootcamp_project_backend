package com.oocl.springbootdemo.repository.dao;

import com.oocl.springbootdemo.object.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
}
