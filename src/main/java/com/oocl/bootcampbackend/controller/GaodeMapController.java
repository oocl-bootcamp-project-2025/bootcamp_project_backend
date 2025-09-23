package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.oocl.bootcampbackend.service.GaodeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gaode")
public class GaodeMapController {
    @Autowired
    private GaodeMapService gaodeMapService;

    /**
     * 获取景点名称对应的经纬度
     * POST /api/gaode/locations
     * body: ["天安门", "故宫"]
     */
    @PostMapping("/locations")
    public ResponseEntity<Map<String, String>> getLocations(@RequestBody List<String> names) throws IOException {
        Map<String, String> result = gaodeMapService.getLocationByName(names);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取驾车路线详细信息
     * POST /api/gaode/drivingRoute
     * body: ["116.397128,39.916527", "116.397026,39.918058", "116.397389,39.908722"]
     */
    @PostMapping("/drivingRoute")
    public ResponseEntity<JsonNode> getDrivingRoute(@RequestBody List<String> locations) throws IOException {
        JsonNode result = gaodeMapService.getDrivingRoute(locations);
        return ResponseEntity.ok(result);
    }
}

