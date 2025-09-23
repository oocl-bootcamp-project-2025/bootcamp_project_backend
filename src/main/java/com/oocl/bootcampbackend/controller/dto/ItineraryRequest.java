package com.oocl.bootcampbackend.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
public class ItineraryRequest {
    private Integer allNumber;
    private Map<String, TripsDTO> itineraryData = new HashMap<>(); // 用Map存储不同天数的行程，key为day1、day2等
    private String phone;
    private String startDate;

    public Integer getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Integer allNumber) {
        this.allNumber = allNumber;
    }

    public Map<String, TripsDTO> getItineraryData() {
        return itineraryData;
    }

    public void setItineraryData(Map<String, TripsDTO> itineraryData) {
        this.itineraryData = itineraryData;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
