package com.oocl.bootcampbackend.controller.dto;

import java.util.List;

public class PathDTO {
    private String distance;
    private String restriction;
    private CostDTO cost;
    private List<StepDTO> steps;

    public PathDTO(String distance, String restriction, CostDTO cost, List<StepDTO> steps) {
        this.distance = distance;
        this.restriction = restriction;
        this.cost = cost;
        this.steps = steps;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public CostDTO getCost() {
        return cost;
    }

    public void setCost(CostDTO cost) {
        this.cost = cost;
    }

    public List<StepDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDTO> steps) {
        this.steps = steps;
    }
}
