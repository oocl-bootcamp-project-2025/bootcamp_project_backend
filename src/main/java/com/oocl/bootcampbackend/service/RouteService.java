package com.oocl.bootcampbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.controller.dto.*;
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
import java.util.Arrays;
import java.util.List;

@Service
public class RouteService {
    private final int dailyAttractionCount = 3;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String ITINERARY_API_URL = "https://sito-routeplanner.up.railway.app/api/tsp/solver/distance";
    private static final String ROUTE_API_URL = "https://sito-routeplanner.up.railway.app/api/routePlanner";
    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private ViewpointRepository viewpointRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private KouziAgentService kouziAgentService;

    private List<Attraction> getAttractions(List<Viewpoint> viewpoints) {
        // TODO: implement full version to balance time
        return viewpoints.stream()
                .map(viewpoint -> attractionRepository.findByName(viewpoint.getName()))
                .filter(attraction -> attraction != null)
                .flatMap(List::stream)
                .toList();
    }

    private List<List<Attraction>> itineraryPlanner(int[] preference, int days) {
        List<Viewpoint> viewpoints = viewpointRepository.findViewPointsByPreference(preference);
        List<Attraction> attractions = getAttractions(viewpoints).stream().limit((long) days * dailyAttractionCount).toList();

        List<Point> points = attractions.stream()
                .map(attraction -> new Point(attraction.getLongitude(), attraction.getLatitude()))
                .toList();
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);

        List<List<Attraction>> itinerary = new ArrayList<>();
        try {
            // Request body to json
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.debug("Request Body: " + jsonRequestBody);
            String response = HttpService.sendPost(ITINERARY_API_URL, jsonRequestBody);
            logger.debug("Response: " + response);
            JsonNode orderNode = mapper.readTree(response).get("order");
            int[] order = new int[0];
            if (orderNode != null && orderNode.isArray()) {
                order = new int[orderNode.size()];
                for (int i = 0; i < orderNode.size(); i++) {
                    order[i] = orderNode.get(i).asInt();
                }
            }
            for (int i = 0; i < days; i++) {
                List<Attraction> dayRoute = new ArrayList<>();
                for (int j = 0; j < dailyAttractionCount; j++) {
                    int index = i * dailyAttractionCount + j;
                    if (index < order.length) {
                        dayRoute.add(attractions.get(order[index]));
                    }
                }
                itinerary.add(dayRoute);
            }
            return itinerary;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in routePlanner: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public static int compareByCodePoint(String a, String b) {
        int[] ca = a.codePoints().toArray();
        int[] cb = b.codePoints().toArray();
        int n = Math.min(ca.length, cb.length);
        for (int i = 0; i < n; i++) {
            if (ca[i] != cb[i]) return Integer.compare(ca[i], cb[i]);
        }
        return Integer.compare(ca.length, cb.length);
    }

    private List<List<Attraction>> itineraryPlannerWithAI(String area, int[] preference, int days) {
        List<Attraction> attractions = kouziAgentService.getAttractionsFromKouziAgentForService(area, Arrays.stream(preference).boxed().toList(), days);
        attractions.sort((a1, a2) -> compareByCodePoint(a1.getName(), a2.getName()));

        List<Point> points = attractions.stream()
                .map(attraction -> new Point(attraction.getLongitude(), attraction.getLatitude()))
                .toList();
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);

        List<List<Attraction>> itinerary = new ArrayList<>();

        try {
            // Request body to json
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.debug("Request Body: " + jsonRequestBody);
            String response = HttpService.sendPost(ITINERARY_API_URL, jsonRequestBody);
            logger.debug("Response: " + response);
            JsonNode orderNode = mapper.readTree(response).get("order");
            int[] order = new int[0];
            if (orderNode != null && orderNode.isArray()) {
                order = new int[orderNode.size()];
                for (int i = 0; i < orderNode.size(); i++) {
                    order[i] = orderNode.get(i).asInt();
                }
            }
            for (int i = 0; i < days; i++) {
                List<Attraction> dayRoute = new ArrayList<>();
                for (int j = 0; j < dailyAttractionCount; j++) {
                    int index = i * dailyAttractionCount + j;
                    if (index < order.length) {
                        dayRoute.add(attractions.get(order[index]));
                    }
                }
                itinerary.add(dayRoute);
            }
            return itinerary;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in routePlanner: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<List<Attraction>> itineraryPlannerWithAttractions(List<Attraction> attractions, int days) {
        attractions.sort((a1, a2) -> compareByCodePoint(a1.getName(), a2.getName()));
        List<Point> points = attractions.stream()
                .map(attraction -> new Point(attraction.getLongitude(), attraction.getLatitude()))
                .toList();
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);

        List<List<Attraction>> itinerary = new ArrayList<>();

        try {
            // Request body to json
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.debug("Request Body: " + jsonRequestBody);
            String response = HttpService.sendPost(ITINERARY_API_URL, jsonRequestBody);
            logger.debug("Response: " + response);
            JsonNode orderNode = mapper.readTree(response).get("order");
            int[] order = new int[0];
            if (orderNode != null && orderNode.isArray()) {
                order = new int[orderNode.size()];
                for (int i = 0; i < orderNode.size(); i++) {
                    order[i] = orderNode.get(i).asInt();
                }
            }
            for (int i = 0; i < days; i++) {
                List<Attraction> dayRoute = new ArrayList<>();
                for (int j = 0; j < dailyAttractionCount; j++) {
                    int index = i * dailyAttractionCount + j;
                    if (index < order.length) {
                        dayRoute.add(attractions.get(order[index]));
                    }
                }
                itinerary.add(dayRoute);
            }
            return itinerary;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in routePlanner: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<Point> getPointsFromItinerary(List<List<Attraction>> itinerary) {
        List<Point> points = new ArrayList<>();
        for (List<Attraction> day : itinerary) {
            for (Attraction attraction : day) {
                points.add(new Point(attraction.getLongitude(), attraction.getLatitude()));
            }
        }
        return points;
    }

    public OptimizedRouteDTO routePlanner(String area, int[] preference, int days) {
        List<List<Attraction>> itinerary = itineraryPlanner(preference, days);
        List<Point> points = getPointsFromItinerary(itinerary);
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);
        String response;
        try {
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.debug("Request Body: " + jsonRequestBody);
            response = HttpService.sendPost(ROUTE_API_URL, jsonRequestBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            JsonNode rootNode = mapper.readTree(response);
            logger.info("Route Response: {}", mapper.writeValueAsString(rootNode));

            JsonNode routeNode = rootNode.get("route");
            if (routeNode == null) {
                logger.error("No route data found in response");
                throw new JsonProcessingException("No route data found") {};
            }
            List<PathDTO> paths = new ArrayList<>();
            JsonNode pathsNode = routeNode.get("paths");
            if (pathsNode == null || !pathsNode.isArray()) {
                throw new JsonProcessingException("Invalid paths data") {
                };
            }
            for (JsonNode path : pathsNode) {
                JsonNode pathCostNode = path.get("cost");
                CostDTO cost = new CostDTO(
                        pathCostNode.get("duration").asText(),
                        pathCostNode.get("tolls").asText(),
                        pathCostNode.get("toll_distance").asText(),
                        pathCostNode.get("traffic_lights").asText()
                );
                List<StepDTO> steps = new ArrayList<>();
                JsonNode stepsNode = path.get("steps");
                for (JsonNode step : stepsNode) {
                    JsonNode stepCostNode = step.get("cost");
                    CostDTO costDTO = new CostDTO(
                            stepCostNode.get("duration").asText(),
                            stepCostNode.get("tolls").asText(),
                            stepCostNode.get("toll_distance").asText(),
                            stepCostNode.get("traffic_lights").asText()
                    );
                    StepDTO stepDTO = new StepDTO(
                            step.get("instruction").asText(),
                            step.get("orientation").asText(),
                            step.get("step_distance").asText(),
                            costDTO,
                            step.get("polyline").asText()
                    );
                    steps.add(stepDTO);
                }
                PathDTO pathDTO = new PathDTO(
                        path.get("distance").asText(),
                        path.get("restriction").asText(),
                        cost,
                        steps
                );
                paths.add(pathDTO);
            }
            RouteDTO route = new RouteDTO(
                    routeNode.get("origin").asText(),
                    routeNode.get("destination").asText(),
                    routeNode.get("taxi_cost").asText(),
                    paths
            );
            OptimizedRouteDTO optimizedRouteDTO = new OptimizedRouteDTO(itinerary, route);
            logger.info("OptimizedRouteDTO: {}", mapper.writeValueAsString(optimizedRouteDTO));
            return optimizedRouteDTO;
        } catch (Exception e) {
            logger.error("Error in routePlanner: {}", e.getMessage());
            return null;
        }
    }

    public OptimizedRouteDTO routePlannerByAI(String area, int[] preference, int days) {
        List<List<Attraction>> itinerary = itineraryPlannerWithAI(area, preference, days);
        List<Point> points = getPointsFromItinerary(itinerary);
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);
        String response;
        try {
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.debug("Request Body: " + jsonRequestBody);
            response = HttpService.sendPost(ROUTE_API_URL, jsonRequestBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            JsonNode rootNode = mapper.readTree(response);
            logger.info("Route Response: {}", mapper.writeValueAsString(rootNode));

            JsonNode routeNode = rootNode.get("route");
            if (routeNode == null) {
                logger.error("No route data found in response");
                throw new JsonProcessingException("No route data found") {};
            }
            List<PathDTO> paths = new ArrayList<>();
            JsonNode pathsNode = routeNode.get("paths");
            if (pathsNode == null || !pathsNode.isArray()) {
                throw new JsonProcessingException("Invalid paths data") {
                };
            }
            for (JsonNode path : pathsNode) {
                JsonNode pathCostNode = path.get("cost");
                CostDTO cost = new CostDTO(
                        pathCostNode.get("duration").asText(),
                        pathCostNode.get("tolls").asText(),
                        pathCostNode.get("toll_distance").asText(),
                        pathCostNode.get("traffic_lights").asText()
                );
                List<StepDTO> steps = new ArrayList<>();
                JsonNode stepsNode = path.get("steps");
                for (JsonNode step : stepsNode) {
                    JsonNode stepCostNode = step.get("cost");
                    CostDTO costDTO = new CostDTO(
                            stepCostNode.get("duration").asText(),
                            stepCostNode.get("tolls").asText(),
                            stepCostNode.get("toll_distance").asText(),
                            stepCostNode.get("traffic_lights").asText()
                    );
                    StepDTO stepDTO = new StepDTO(
                            step.get("instruction").asText(),
                            step.get("orientation").asText(),
                            step.get("step_distance").asText(),
                            costDTO,
                            step.get("polyline").asText()
                    );
                    steps.add(stepDTO);
                }
                PathDTO pathDTO = new PathDTO(
                        path.get("distance").asText(),
                        path.get("restriction").asText(),
                        cost,
                        steps
                );
                paths.add(pathDTO);
            }
            RouteDTO route = new RouteDTO(
                    routeNode.get("origin").asText(),
                    routeNode.get("destination").asText(),
                    routeNode.get("taxi_cost").asText(),
                    paths
            );
            OptimizedRouteDTO optimizedRouteDTO = new OptimizedRouteDTO(itinerary, route);
            logger.info("OptimizedRouteDTO: {}", mapper.writeValueAsString(optimizedRouteDTO));
            return optimizedRouteDTO;
        } catch (Exception e) {
            logger.error("Error in routePlanner: {}", e.getMessage());
            return null;
        }
    }

    public OptimizedRouteDTO getRoutePlanByAttractions(List<Attraction> attractions, int days) {
        List<List<Attraction>> itinerary = itineraryPlannerWithAttractions(attractions, days);
        List<Point> points = getPointsFromItinerary(itinerary);
        RouteRequestBody requestBody = new RouteRequestBody(points, 1);
        String response;
        try {
            String jsonRequestBody = mapper.writeValueAsString(requestBody);
            logger.debug("Request Body: " + jsonRequestBody);
            response = HttpService.sendPost(ROUTE_API_URL, jsonRequestBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            JsonNode rootNode = mapper.readTree(response);
            logger.info("Route Response: {}", mapper.writeValueAsString(rootNode));

            JsonNode routeNode = rootNode.get("route");
            if (routeNode == null) {
                logger.error("No route data found in response");
                throw new JsonProcessingException("No route data found") {};
            }
            List<PathDTO> paths = new ArrayList<>();
            JsonNode pathsNode = routeNode.get("paths");
            if (pathsNode == null || !pathsNode.isArray()) {
                throw new JsonProcessingException("Invalid paths data") {
                };
            }
            for (JsonNode path : pathsNode) {
                JsonNode pathCostNode = path.get("cost");
                CostDTO cost = new CostDTO(
                        pathCostNode.get("duration").asText(),
                        pathCostNode.get("tolls").asText(),
                        pathCostNode.get("toll_distance").asText(),
                        pathCostNode.get("traffic_lights").asText()
                );
                List<StepDTO> steps = new ArrayList<>();
                JsonNode stepsNode = path.get("steps");
                for (JsonNode step : stepsNode) {
                    JsonNode stepCostNode = step.get("cost");
                    CostDTO costDTO = new CostDTO(
                            stepCostNode.get("duration").asText(),
                            stepCostNode.get("tolls").asText(),
                            stepCostNode.get("toll_distance").asText(),
                            stepCostNode.get("traffic_lights").asText()
                    );
                    StepDTO stepDTO = new StepDTO(
                            step.get("instruction").asText(),
                            step.get("orientation").asText(),
                            step.get("step_distance").asText(),
                            costDTO,
                            step.get("polyline").asText()
                    );
                    steps.add(stepDTO);
                }
                PathDTO pathDTO = new PathDTO(
                        path.get("distance").asText(),
                        path.get("restriction").asText(),
                        cost,
                        steps
                );
                paths.add(pathDTO);
            }
            RouteDTO route = new RouteDTO(
                    routeNode.get("origin").asText(),
                    routeNode.get("destination").asText(),
                    routeNode.get("taxi_cost").asText(),
                    paths
            );
            OptimizedRouteDTO optimizedRouteDTO = new OptimizedRouteDTO(itinerary, route);
            logger.info("OptimizedRouteDTO: {}", mapper.writeValueAsString(optimizedRouteDTO));
            return optimizedRouteDTO;
        } catch (Exception e) {
            logger.error("Error in routePlanner: {}", e.getMessage());
            return null;
        }
    }
}
