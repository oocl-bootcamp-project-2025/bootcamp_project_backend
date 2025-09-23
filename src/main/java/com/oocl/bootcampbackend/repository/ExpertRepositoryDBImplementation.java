package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Expert;
import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.repository.dao.ExpertJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpertRepositoryDBImplementation implements ExpertRepository {

    @Autowired
    private ExpertJpaRepository expertJpaRepository;

    public List<Expert> findAllExpert() {
        return expertJpaRepository.findAll();
    }

//    public List<Expert> findAllExpertAvailable(String time) {
//        return expertJpaRepository
//                .findAll()
//                .stream()
//                .filter(expert -> expert.isAvailableAt(time))
//                .toList();
//    }
}
