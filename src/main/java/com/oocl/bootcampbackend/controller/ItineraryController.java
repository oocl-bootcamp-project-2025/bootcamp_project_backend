package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.ItineraryDTO;
import com.oocl.bootcampbackend.entity.Itinerary;
import com.oocl.bootcampbackend.service.ItineraryService;
import com.oocl.bootcampbackend.service.ViewpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itineraries")
public class ItineraryController {
    @Autowired
    private ItineraryService itineraryService;

    @PostMapping("")
    public ResponseEntity<Itinerary> saveItinerary(@RequestBody ItineraryDTO itineraryDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(itineraryService.save(itineraryDTO));
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<List<Itinerary>> getItineraryByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(itineraryService.getItineraryByPhoneNumber(phoneNumber));
    }

    @GetMapping("/itineraryData/{id}")
    public ResponseEntity<String> getItineraryDataByItineraryId(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itineraryService.getItineraryDataByItineraryId(id));
    }
}
