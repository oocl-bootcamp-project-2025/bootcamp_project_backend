package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripController
{
    @Autowired
    private TripService tripService;

    @PostMapping("/trips")
    public ResponseEntity<Void> saveTrips(@RequestBody ItineraryRequest itineraryRequest){
        tripService.save(itineraryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/trips")
    public ResponseEntity<List<TripDTO>> getTrips(){
        List<TripDTO> trips = tripService.findAll();
        return ResponseEntity.ok(trips);
    }

}
