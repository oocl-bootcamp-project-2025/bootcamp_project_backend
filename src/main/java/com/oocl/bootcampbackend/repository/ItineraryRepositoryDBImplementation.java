package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.controller.dto.ItineraryDTO;
import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.entity.ItineraryData;
import com.oocl.bootcampbackend.repository.dao.ItineraryDataJpaRepository;
import com.oocl.bootcampbackend.repository.dao.ItineraryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItineraryRepositoryDBImplementation implements ItineraryRepository {

    @Autowired
    private ItineraryJpaRepository itineraryJpaRepository;

    @Autowired
    private ItineraryDataJpaRepository itineraryDataJpaRepository;

    public Itinerary save(ItineraryDTO itineraryDTO) {
        ItineraryData itineraryData = new ItineraryData();
        itineraryData.setItineraryData(itineraryDTO.getItineraryData());
        ItineraryData savedItineraryData = itineraryDataJpaRepository.save(itineraryData);

        Itinerary itinerary = new Itinerary();
        itinerary.setTitle(itineraryDTO.getTitle());
        itinerary.setDescription(itineraryDTO.getDescription());
        itinerary.setPhoneNumber(itineraryDTO.getPhoneNumber());
        itinerary.setStartDate(itineraryDTO.getStartDate());
        itinerary.setAllNumber(itineraryDTO.getAllNumber());
        itinerary.setItineraryDataId(savedItineraryData.getId());
        return itineraryJpaRepository.save(itinerary);
    }

    public List<Itinerary> getItineraryByPhoneNumber(String phoneNumber) {
        return itineraryJpaRepository.findByPhoneNumber(phoneNumber);
    }

    public ItineraryData getItineraryDataByItineraryId(long itineraryId) {
        return itineraryDataJpaRepository
                .findById(itineraryId)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
