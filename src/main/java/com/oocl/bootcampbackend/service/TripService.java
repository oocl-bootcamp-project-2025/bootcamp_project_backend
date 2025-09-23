package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.exception.NotExistingUserException;
import com.oocl.bootcampbackend.exception.NullPhoneException;
import com.oocl.bootcampbackend.repository.TripRepository;
import com.oocl.bootcampbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public void save(ItineraryRequest itineraryRequest) {
        String phone = itineraryRequest.getPhoneNumber();
        if (phone == null || phone.isEmpty()){
            throw new NullPhoneException("phone is null");
        }
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new NotExistingUserException("user is not exist");
        }

        Map<String, List<TripDTO>> itineraryData = itineraryRequest.getItineraryData();
        for (Map.Entry<String, List<TripDTO>> dayEntry : itineraryData.entrySet()) {
            String dayKey = dayEntry.getKey();
            List<TripDTO> tripDTOList = dayEntry.getValue();
            for (TripDTO tripDTO : tripDTOList) {
                Trip trip = new Trip();
                // trip.setId(tripDTO.getId()); // id由数据库自增
                trip.setName(tripDTO.getName());
                trip.setDescription(tripDTO.getDescription());
                trip.setDuration(tripDTO.getDuration());
                trip.setTime(tripDTO.getTime());
                trip.setLocation(tripDTO.getLocation());
                trip.setImages(String.join(",", tripDTO.getImages()));
                trip.setExperts(String.join(",", tripDTO.getExperts()));
                trip.setStart(tripDTO.getStart());
                trip.setDay(dayKey); // 关联到day1、day2等
                trip.setUser(user); // 关联到当前用户
                trip.setDone(false); // 默认未完成
                tripRepository.save(trip);
                user.getTrips().add(trip);
            }
        }
    }
}
