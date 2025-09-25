package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.controller.dto.ItineraryDTO;
import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.entity.ItineraryData;

import java.util.List;

public interface ItineraryRepository {
    Itinerary save(ItineraryDTO itineraryDTO);
    List<Itinerary> getItineraryByPhoneNumber(String phoneNumber);
    ItineraryData getItineraryDataByItineraryId(long itineraryId);
}
