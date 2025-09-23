package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.repository.TripRepository;
import com.oocl.bootcampbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    public void save(TripDTO tripDTO, String phone) {
        User user = userRepository.findByPhone(phone);
        Trip trip = new Trip();
        trip.setName(tripDTO.getName());
        // 将tripDTO的数据全部传输到Trip内
        trip.setDescription(tripDTO.getDescription());
        trip.setDuration(tripDTO.getDuration());
        trip.setTime(tripDTO.getTime());
        trip.setLocation(tripDTO.getLocation());
        trip.setImages(tripDTO.getImages());
        trip.setExperts(tripDTO.getExperts());
        trip.setStart(tripDTO.getStart());
        trip.setUser(user);
        tripRepository.save(trip);
    }
}
