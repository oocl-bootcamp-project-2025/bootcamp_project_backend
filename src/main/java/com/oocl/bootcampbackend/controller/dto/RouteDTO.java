package com.oocl.bootcampbackend.controller.dto;

import java.util.List;

public class RouteDTO {
    private String origin;
    private String destination;
    private String taxi_cost;
    private List<PathDTO> paths;

    public RouteDTO(String origin, String destination, String taxi_cost, List<PathDTO> paths) {
        this.origin = origin;
        this.destination = destination;
        this.taxi_cost = taxi_cost;
        this.paths = paths;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTaxi_cost() {
        return taxi_cost;
    }

    public void setTaxi_cost(String taxi_cost) {
        this.taxi_cost = taxi_cost;
    }

    public List<PathDTO> getPaths() {
        return paths;
    }

    public void setPaths(List<PathDTO> paths) {
        this.paths = paths;
    }
}
