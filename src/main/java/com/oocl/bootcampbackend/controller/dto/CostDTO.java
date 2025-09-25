package com.oocl.bootcampbackend.controller.dto;

import java.util.List;

public class CostDTO {
    private String duration;
    private String tolls;
    private String toll_distance;
    private String traffic_lights;

    public CostDTO(String duration, String tolls, String toll_distance, String traffic_lights) {
        this.duration = duration;
        this.tolls = tolls;
        this.toll_distance = toll_distance;
        this.traffic_lights = traffic_lights;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTolls() {
        return tolls;
    }

    public void setTolls(String tolls) {
        this.tolls = tolls;
    }

    public String getTraffic_lights() {
        return traffic_lights;
    }

    public void setTraffic_lights(String traffic_lights) {
        this.traffic_lights = traffic_lights;
    }

    public String getToll_distance() {
        return toll_distance;
    }

    public void setToll_distance(String toll_distance) {
        this.toll_distance = toll_distance;
    }
}
