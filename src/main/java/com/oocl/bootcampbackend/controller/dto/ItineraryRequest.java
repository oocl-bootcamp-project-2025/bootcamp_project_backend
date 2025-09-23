package com.oocl.bootcampbackend.controller.dto;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ItineraryRequest {
    private Integer allNumber;
    private Map<String, List<TripDTO>> itineraryData = new HashMap<>(); // 用Map存储不同天数的行程，key为day1、day2等
    private String phoneNumber;
    private String startDate;

    public Integer getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Integer allNumber) {
        this.allNumber = allNumber;
    }

    public Map<String, List<TripDTO>> getItineraryData() {
        return itineraryData;
    }

    public void setItineraryData(Map<String, List<TripDTO>> itineraryData) {
        this.itineraryData = itineraryData;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
