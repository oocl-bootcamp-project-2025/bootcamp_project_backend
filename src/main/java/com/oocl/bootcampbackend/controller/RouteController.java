package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.OptimizedRouteDTO;
import com.oocl.bootcampbackend.entity.Attraction;
import com.oocl.bootcampbackend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping("/planner")
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

    @PostMapping("plannerByAttractions")
    public OptimizedRouteDTO getRoutePlanByAttractions(@RequestBody List<Attraction> attractions,@RequestParam int days) {
        return routeService.getRoutePlanByAttractions(attractions,days);
    }
}
