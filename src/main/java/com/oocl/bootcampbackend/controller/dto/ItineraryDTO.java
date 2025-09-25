package com.oocl.bootcampbackend.controller.dto;

public class ItineraryDTO {
    private String title;
    private String description;
    private String phoneNumber;
    private String startDate;
    private int allNumber;
    private String itineraryData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }

    public String getItineraryData() {
        return itineraryData;
    }

    public void setItineraryData(String itineraryData) {
        this.itineraryData = itineraryData;
    }
}
