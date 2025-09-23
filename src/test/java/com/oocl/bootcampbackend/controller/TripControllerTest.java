package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.controller.TripController;
import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.controller.dto.TripsDTO;
import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.repository.UserRepository;
import com.oocl.bootcampbackend.service.TripService;
import com.oocl.bootcampbackend.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TripControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void should_return_success_when_post_given_trips_day_phone() throws Exception {
        // 1. 构建测试用的请求数据（ItineraryRequest）
        ItineraryRequest request = createTestItineraryRequest();

        // 2. 先往数据库存数据
        userRepository.save(createTestUser());

        // 3. 执行HTTP POST请求并验证结果
        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


    @Test
    public void should_throw_exception_when_post_given_nullPhone() throws Exception {
        ItineraryRequest invalidRequest = createTestItineraryRequest();
        invalidRequest.setPhone("");

        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("phone is null"));
    }



    private User createTestUser() {
        User user = new User();
        user.setPhone("13800138000");
        user.setPassword("111111");
        return user;
    }

    private ItineraryRequest createTestItineraryRequest() {
        ItineraryRequest request = new ItineraryRequest();
        request.setAllNumber(2); // 总天数
        request.setPhone("13800138000"); // 测试手机号
        request.setStartDate("2024-10-01");
        Map<String, TripsDTO> itineraryData = new HashMap<>();

        // 第一天行程
        TripsDTO day1Trips = new TripsDTO();
        List<TripDTO> day1List = new ArrayList<>();
        TripDTO trip1 = new TripDTO();
        trip1.setName("天安门广场");
        trip1.setLocation("北京");
        trip1.setDuration("2小时");
        day1List.add(trip1);
        day1Trips.setTrips(day1List);
        itineraryData.put("day1", day1Trips);

        // 第二天行程
        TripsDTO day2Trips = new TripsDTO();
        List<TripDTO> day2List = new ArrayList<>();
        TripDTO trip2 = new TripDTO();
        trip2.setName("长城");
        trip2.setLocation("北京延庆");
        trip2.setDuration("5小时");
        day2List.add(trip2);
        day2Trips.setTrips(day2List);
        itineraryData.put("day2", day2Trips);

        request.setItineraryData(itineraryData);
        return request;
    }
}
