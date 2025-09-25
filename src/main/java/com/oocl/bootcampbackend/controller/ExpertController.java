package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.entity.Expert;
import com.oocl.bootcampbackend.service.ExpertService;
import com.oocl.bootcampbackend.service.ViewpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/experts")
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    @GetMapping("/{locationId}")
    public ResponseEntity<List<Expert>> getAreas(@PathVariable long locationId) {
        return ResponseEntity.status(HttpStatus.OK).body(expertService.findExpertsByLocationId(locationId));
    }
}
