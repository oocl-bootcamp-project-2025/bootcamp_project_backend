package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJpaRepository extends JpaRepository<Employee,Long> {
    @Override
    Page<Employee> findAll(Pageable pageable);

    List<Employee> findById(long id);

    List<Employee> findByGender(String gender);


}
