package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.controller.dto.ItineraryDTO;
import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.entity.ItineraryData;
import com.oocl.bootcampbackend.repository.ItineraryRepository;
import com.oocl.bootcampbackend.repository.ViewpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    public Itinerary save(ItineraryDTO itineraryDTO) {
        return itineraryRepository.save(itineraryDTO);
    }

    public List<Itinerary> getItineraryByPhoneNumber(String phoneNumber) {
        return itineraryRepository.getItineraryByPhoneNumber(phoneNumber);
    }

    public String getItineraryDataByItineraryId(long itineraryId) {
        ItineraryData itineraryData = itineraryRepository.getItineraryDataByItineraryId(itineraryId);
        if (itineraryData != null) {
            return itineraryData.getItineraryData();
        }
        return null;
    }
}
