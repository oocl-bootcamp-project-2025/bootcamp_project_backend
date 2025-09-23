package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.controller.dto.TripsDTO;
import com.oocl.bootcampbackend.entity.Trip;
import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.exception.NullPhoneException;
import com.oocl.bootcampbackend.repository.TripRepository;
import com.oocl.bootcampbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public void save(ItineraryRequest itineraryRequest) {
        String phone = itineraryRequest.getPhone();
        // 1. 根据手机号查询用户（确保用户存在）
        if (phone.isEmpty()){
            throw new NullPhoneException("phone is null");
        }
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new RuntimeException();
        }

        // 2. 遍历外层天数映射（day1、day2等）
        Map<String, TripsDTO> itineraryData = itineraryRequest.getItineraryData();
        for (Map.Entry<String, TripsDTO> dayEntry : itineraryData.entrySet()) {
            String dayKey = dayEntry.getKey(); // 天数标识（day1、day2...）
            TripsDTO tripsDTO = dayEntry.getValue(); // 该天的行程集合

            // 3. 遍历当天的所有具体行程（TripDTO列表）
            List<TripDTO> tripDTOList = tripsDTO.getTrips();
            for (TripDTO tripDTO : tripDTOList) {
                // 4. 创建Trip实体并设置属性
                Trip trip = new Trip();

                // 4.1 设置从TripDTO来的字段
                trip.setName(tripDTO.getName());
                trip.setDescription(tripDTO.getDescription());
                trip.setDuration(tripDTO.getDuration());
                trip.setTime(tripDTO.getTime());
                trip.setLocation(tripDTO.getLocation());
                trip.setImages(tripDTO.getImages());
                trip.setExperts(tripDTO.getExperts());
                trip.setStart(tripDTO.getStart());

                // 4.2 设置天数和关联用户
                trip.setDay(dayKey); // 关联到day1、day2等
                trip.setUser(user); // 关联到当前用户
                trip.setDone(false); // 默认未完成

                // 5. 保存单个行程记录
                tripRepository.save(trip);

                user.getTrips().add(trip);
            }
        }}
}
