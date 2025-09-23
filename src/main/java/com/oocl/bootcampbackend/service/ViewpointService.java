package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.repository.ViewpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewpointService {

    @Autowired
    private ViewpointRepository viewpointRepository;

    public List<String> getArea(String area) {
        return viewpointRepository.findArea(area);
    }

    public List<String> getAllArea() {
        return viewpointRepository.findAllAreaTemp();
    }
}
