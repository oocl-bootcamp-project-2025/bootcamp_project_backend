package com.oocl.bootcampbackend.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDTO {
    private String name;
    private String description;
    private String duration;
    private String time;
    private String location;
    private String images;
    private String experts;
    private String start;
}
