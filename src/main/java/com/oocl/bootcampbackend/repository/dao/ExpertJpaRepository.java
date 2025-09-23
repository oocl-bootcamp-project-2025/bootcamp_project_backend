package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Expert;
import com.oocl.bootcampbackend.entity.Viewpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpertJpaRepository extends JpaRepository<Expert, Long> {

}
