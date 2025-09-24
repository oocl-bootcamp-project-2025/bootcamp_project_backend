package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TripControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void should_return_success_when_post_given_trips_day_phone() throws Exception {
        ItineraryRequest request = createTestItineraryRequest();

        accountRepository.save(createTestUser());

        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


    @Test
    public void should_throw_exception_when_post_given_nullPhone() throws Exception {
        ItineraryRequest invalidRequest = createTestItineraryRequest();
        invalidRequest.setPhoneNumber("");

        mockMvc.perform(post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("phone is null"));
    }



    private Account createTestUser() {
        Account account = new Account();
        account.setPhone("13800138000");
        account.setPassword("111111");
        return account;
    }

    private ItineraryRequest createTestItineraryRequest() {
        ItineraryRequest request = new ItineraryRequest();
    request.setAllNumber(2); // 总天数
    request.setPhoneNumber("13800138000"); // 测试手机号
    request.setStartDate("2024-10-01");
    Map<String, List<TripDTO>> itineraryData = new HashMap<>();

    // 第一天行程
    List<TripDTO> day1List = new ArrayList<>();
    TripDTO trip1 = new TripDTO();
    trip1.setName("天安门广场");
    trip1.setLocation("北京");
    trip1.setDuration("2小时");
    TripDTO trip12 = new TripDTO();
    trip12.setName("天安门广场");
    trip12.setLocation("北京");
    trip12.setDuration("2小时");
    day1List.add(trip1);
    day1List.add(trip12);
    itineraryData.put("day1", day1List);

    // 第二天行程
    List<TripDTO> day2List = new ArrayList<>();
    TripDTO trip2 = new TripDTO();
    trip2.setName("长城");
    trip2.setLocation("北京延庆");
    trip2.setDuration("5小时");
    day2List.add(trip2);
    itineraryData.put("day2", day2List);

    request.setItineraryData(itineraryData);
    return request;
    }
}
