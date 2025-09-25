package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.entity.ItineraryData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryDataJpaRepository extends JpaRepository<ItineraryData, Long> {
}
