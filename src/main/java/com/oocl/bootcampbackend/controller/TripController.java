package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.controller.dto.TripsDTO;
import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController
{
    @Autowired
    private TripService tripService;

    @PostMapping("/trips")
    public ResponseEntity<Void> saveTrips(@RequestBody ItineraryRequest itineraryRequest){
        // 1. 转化成Trip列表
//        TripsDTO tripDTOs = itineraryRequest.getTrips();
        // 2. 存进数据库
        tripService.save(itineraryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
