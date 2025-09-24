package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Attraction;

import java.util.List;

public interface AttractionRepository {
    List<Attraction> findByName(String name);
}
