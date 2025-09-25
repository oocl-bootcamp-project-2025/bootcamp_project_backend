package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Viewpoint;

import java.util.List;

public interface ViewpointRepository {
    Viewpoint save(Viewpoint viewpoint);
    List<String> findArea(String area);
    List<String> findAllArea();
    List<Viewpoint> findViewPointsByPreference(int[] preference);
    List<List<String>> findAllAreaWithLocation();
}
