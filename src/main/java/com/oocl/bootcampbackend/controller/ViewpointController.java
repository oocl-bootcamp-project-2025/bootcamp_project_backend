package com.oocl.bootcampbackend.controller;

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
@RequestMapping("/viewpoints")
public class ViewpointController {

    @Autowired
    private ViewpointService viewpointService;

    @GetMapping("/{area}")
    public ResponseEntity<List<String>> getAreas(@PathVariable String area) {
        return ResponseEntity.status(HttpStatus.OK).body(viewpointService.getArea(area));
    }
}
