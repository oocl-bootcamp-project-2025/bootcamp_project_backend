package com.oocl.bootcampbackend.controller.dto;

public class RouteDTO {
    String destination;
    int duration;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
