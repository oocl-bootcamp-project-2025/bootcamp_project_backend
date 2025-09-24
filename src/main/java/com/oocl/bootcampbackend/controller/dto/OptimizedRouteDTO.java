package com.oocl.bootcampbackend.controller.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oocl.bootcampbackend.entity.Attraction;

import java.util.List;

public class OptimizedRouteDTO {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonNode itinerary;
    private RouteDTO route;

    public OptimizedRouteDTO(List<List<Attraction>> attractions , RouteDTO route) {
        ObjectNode itinerary = objectMapper.createObjectNode();
        for (int i = 0; i < attractions.size(); i++) {
            itinerary.set("day" + (i + 1), objectMapper.valueToTree(attractions.get(i)));
        }
        this.itinerary = itinerary;
        this.route = route;
    }

    public JsonNode getItinerary() {
        return itinerary;
    }

    public void setItinerary(JsonNode itinerary) {
        this.itinerary = itinerary;
    }

    public RouteDTO getRoute() {
        return route;
    }

    public void setRoute(RouteDTO route) {
        this.route = route;
    }
}
