package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Viewpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewpointJpaRepository extends JpaRepository<Viewpoint, Long> {
    List<Viewpoint> findByAreaContaining(String area);
}
