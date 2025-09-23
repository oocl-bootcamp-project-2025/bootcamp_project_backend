package com.oocl.bootcampbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "expert")
public class Expert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String icon;
    private String area;
    private String service;
    private Double price;
    private String bookedTimeslot;
    private String xiaohongshu;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBookedTimeslot() {
        return bookedTimeslot;
    }

    public void setBookedTimeslot(String bookedTimeslot) {
        this.bookedTimeslot = bookedTimeslot;
    }

    public String getXiaohongshu() {
        return xiaohongshu;
    }

    public void setXiaohongshu(String xiaohongshu) {
        this.xiaohongshu = xiaohongshu;
    }
}
