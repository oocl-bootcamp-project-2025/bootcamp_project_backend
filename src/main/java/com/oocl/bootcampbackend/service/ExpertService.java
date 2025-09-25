package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.entity.Expert;
import com.oocl.bootcampbackend.repository.AttractionRepository;
import com.oocl.bootcampbackend.repository.ExpertRepository;
import com.oocl.bootcampbackend.repository.ViewpointRepository;
import com.oocl.bootcampbackend.repository.dao.AttractionJpaRepository;
import com.oocl.bootcampbackend.repository.dao.ExpertJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertService {
    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    public List<Expert> findExpertsByLocationId(long locationId) {
        Attraction attraction = attractionRepository.findById(locationId);
        if (attraction == null) {
            return null;
        }
        return expertRepository
                .findAll()
                .stream()
                .filter(expert -> expert.getLocation().equals(attraction.getName()))
                .toList();
    }
}
