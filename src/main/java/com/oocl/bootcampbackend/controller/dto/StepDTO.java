package com.oocl.bootcampbackend.controller.dto;

public class StepDTO {
    private String instruction;
    private String orientation;
    private String step_distance;
    private CostDTO cost;
    private String polyline;

    public StepDTO(String instruction, String orientation, String step_distance, CostDTO cost, String polyline) {
        this.instruction = instruction;
        this.orientation = orientation;
        this.step_distance = step_distance;
        this.cost = cost;
        this.polyline = polyline;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getStep_distance() {
        return step_distance;
    }

    public void setStep_distance(String step_distance) {
        this.step_distance = step_distance;
    }

    public CostDTO getCost() {
        return cost;
    }

    public void setCost(CostDTO cost) {
        this.cost = cost;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }
}
