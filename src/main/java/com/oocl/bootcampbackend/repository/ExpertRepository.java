package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Expert;

import java.util.List;

public interface ExpertRepository {
    List<Expert> findAll();
}
