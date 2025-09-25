package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertJpaRepository extends JpaRepository<Expert, Long> {
}
