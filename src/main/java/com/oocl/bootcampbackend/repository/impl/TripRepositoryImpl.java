package com.oocl.bootcampbackend.repository.impl;

import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.repository.TripRepository;
import com.oocl.bootcampbackend.repository.dao.TripJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripRepositoryImpl implements TripRepository {
    @Autowired
    private TripJpaRepository tripJpaRepository;
    @Override
    public List<Trip> findAll() {
        return tripJpaRepository.findAll();
    }

    @Override
    public List<Trip> findByAccount(Account account) {
        return tripJpaRepository.findByAccount(account);
    }

    @Override
    public void save(Trip trip) {
        tripJpaRepository.save(trip);
    }
}
