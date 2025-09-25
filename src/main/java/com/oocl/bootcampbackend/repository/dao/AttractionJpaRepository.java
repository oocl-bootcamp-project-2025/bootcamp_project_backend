package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionJpaRepository extends JpaRepository<Attraction, Long> {

}
