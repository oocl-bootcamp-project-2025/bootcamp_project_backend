package com.oocl.bootcampbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.model.Point;
import com.oocl.bootcampbackend.model.RouteRequestBody;
import com.oocl.bootcampbackend.repository.AttractionRepository;
import com.oocl.bootcampbackend.repository.ViewpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final int dailyAttractionCount = 3;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String ROUTE_API_URL = "https://sito-routeplanner.up.railway.app/api/tsp/solver/distance";
    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private ViewpointRepository viewpointRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    private List<Attraction> getAttractions(List<Viewpoint> viewpoints) {
        // TODO: implement full version to balance time
        return viewpoints.stream()
                .map(viewpoint -> attractionRepository.findByName(viewpoint.getName()))
                .filter(attraction -> attraction != null)
                .flatMap(List::stream)
                .toList();
    }

    public List<List<Attraction>> routePlanner(int[] preference, int days) {
        List<Viewpoint> viewpoints = viewpointRepository.findViewPointsByPreference(preference);
        List<Attraction> attractions = getAttractions(viewpoints).stream().limit((long) days * dailyAttractionCount).toList();

        List<Point> points = attractions.stream()
                .map(attraction -> new Point(attraction.getLongitude(), attraction.getLatitude()))
                .toList();
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);

        List<List<Attraction>> route = new ArrayList<>();
        try {
            // Request body to json
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.info("Request Body: " + jsonRequestBody);
            String response = HttpService.sendPost(ROUTE_API_URL, jsonRequestBody);
            logger.info("Response: " + response);
            // Parse the response to json, read "order" field
            int[] order = mapper.readTree(response).get("order").traverse().readValueAs(int[].class);
            for (int i = 0; i < days; i++) {
                List<Attraction> dayRoute = new ArrayList<>();
                for (int j = 0; j < dailyAttractionCount; j++) {
                    int index = i * dailyAttractionCount + j;
                    if (index < order.length) {
                        dayRoute.add(attractions.get(order[index]));
                    }
                }
                route.add(dayRoute);
            }
            return route;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in routePlanner: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
