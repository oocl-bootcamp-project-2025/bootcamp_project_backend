package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.entity.Viewpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryJpaRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findByPhoneNumber(String phoneNumber);
}
