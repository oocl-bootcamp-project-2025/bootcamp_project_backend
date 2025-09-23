package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.RouteDTO;
import com.oocl.bootcampbackend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    RouteService routeService;
    @PostMapping("/process")
    public Map<String, Object> processRoute(@RequestBody RouteDTO routeData) throws IOException {
        return Map.of("result",routeService.processRoute(routeData));
    }
}
