package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.repository.dao.ViewpointJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ViewpointRepositoryDBImplementation implements ViewpointRepository {

    @Autowired
    private ViewpointJpaRepository viewpointJpaRepository;

    public Viewpoint save(Viewpoint viewpoint) {
        return viewpointJpaRepository.save(viewpoint);
    }

    public List<String> findArea(String area) {
        return viewpointJpaRepository
                .findByAreaContaining(area)
                .stream()
                .map(Viewpoint::getArea)
                .toList();
    }

    public List<String> findAllArea() {
        return viewpointJpaRepository
                .findAll()
                .stream()
                .map(Viewpoint::getArea)
                .distinct()
                .toList();
    }
}
