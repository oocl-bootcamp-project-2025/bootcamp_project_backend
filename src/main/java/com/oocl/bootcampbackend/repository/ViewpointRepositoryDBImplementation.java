package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.model.Point;
import com.oocl.bootcampbackend.repository.dao.ViewpointJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<List<String>> findAllAreaWithLocation() {
        return viewpointJpaRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Viewpoint::getArea,
                        Collectors.mapping(Viewpoint::getLocation, Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<String> areaAndLocation = new ArrayList<>();
                    areaAndLocation.add(entry.getKey()); // Add the area
                    if (!entry.getValue().isEmpty()) {
                        areaAndLocation.add(entry.getValue().get(0)); // Add the first location
                    }
                    return areaAndLocation;
                })
                .toList();
    }

    public List<Viewpoint> findViewPointsByPreference(int[] preference) {
        return viewpointJpaRepository
                .findAll()
                .stream()
                .filter(viewpoint -> {
                    String prefs = viewpoint.getPreference();
                    for (int pref : preference) {
                        if (prefs.contains(String.valueOf(pref))) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
    }
}
