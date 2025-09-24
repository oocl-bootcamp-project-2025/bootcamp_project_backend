package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.entity.Attraction;
<<<<<<< HEAD
import com.oocl.bootcampbackend.model.Point;
=======
>>>>>>> dev
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
    public List<List<Attraction>> routePlanner(
            @RequestParam("preference") List<Integer> preference,
            @RequestParam("days") int days
    ) {
        return routeService.routePlanner(preference.stream().mapToInt(i -> i).toArray(), days);
    }
}
