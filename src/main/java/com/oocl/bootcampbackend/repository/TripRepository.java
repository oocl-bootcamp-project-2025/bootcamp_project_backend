package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface TripRepository {
    public List<Trip> findAll();
    public List<Trip> findByUser(User user);
    public void save(Trip trip);
}
