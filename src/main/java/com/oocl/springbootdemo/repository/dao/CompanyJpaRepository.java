package com.oocl.springbootdemo.repository.dao;

import com.oocl.springbootdemo.object.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {
}
