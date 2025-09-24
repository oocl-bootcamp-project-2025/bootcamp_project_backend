package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.repository.dao.AttractionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttractionRepositoryDBImplementation implements AttractionRepository {
    @Autowired
    private AttractionJpaRepository attractionJpaRepository;

    public List<Attraction> findByName(String name) {
        return attractionJpaRepository.findAll()
                .stream()
                .filter(attraction -> attraction.getName().contains(name))
                .toList();
    }
}
