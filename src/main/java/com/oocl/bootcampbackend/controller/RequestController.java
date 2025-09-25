package com.oocl.bootcampbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
