package com.oocl.bootcampbackend.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
//
//@Getter
//@Setter
public class TripsDTO {
    private List<TripDTO> trips = new ArrayList<>();

    public List<TripDTO> getTrips() {
        return trips;
    }

    public void setTrips(List<TripDTO> trips) {
        this.trips = trips;
    }
}
