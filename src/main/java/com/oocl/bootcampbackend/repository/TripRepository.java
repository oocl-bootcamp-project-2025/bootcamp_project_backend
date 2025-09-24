package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository {
    public List<Trip> findAll();
    public List<Trip> findByAccount(Account account);
    public void save(Trip trip);
}
