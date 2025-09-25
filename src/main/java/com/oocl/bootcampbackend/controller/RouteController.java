package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.OptimizedRouteDTO;
import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping("/planner")
    public OptimizedRouteDTO routePlanner(
            @RequestParam("area") String area,
            @RequestParam("preference") List<Integer> preference,
            @RequestParam("days") int days
    ) {
        return routeService.routePlanner(
                area,
                preference.stream().mapToInt(i -> i).toArray(),
                days
        );
    }

    @GetMapping("/plannerByAI")
    public OptimizedRouteDTO routePlannerByAI(
            @RequestParam("area") String area,
            @RequestParam("preference") List<Integer> preference,
            @RequestParam("days") int days
    ) {
        return routeService.routePlannerByAI(
                area,
                preference.stream().mapToInt(i -> i).toArray(),
                days
        );
    }
}
