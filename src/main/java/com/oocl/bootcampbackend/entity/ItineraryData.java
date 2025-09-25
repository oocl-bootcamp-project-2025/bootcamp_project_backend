package com.oocl.bootcampbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "itinerary_data")
public class ItineraryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String itineraryData;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItineraryData() {
        return itineraryData;
    }

    public void setItineraryData(String itineraryData) {
        this.itineraryData = itineraryData;
    }
}
