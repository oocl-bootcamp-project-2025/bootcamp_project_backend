package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripJpaRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByAccount(Account account);
}
