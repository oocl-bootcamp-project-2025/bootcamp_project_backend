package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.controller.TripController;
import com.oocl.bootcampbackend.controller.dto.ItineraryRequest;
import com.oocl.bootcampbackend.controller.dto.TripDTO;
import com.oocl.bootcampbackend.controller.dto.TripsDTO;
import com.oocl.bootcampbackend.controller.dto.UserDTO;
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
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 测试场景：正常注册（手机号和密码均有效）
     * 预期结果：返回201 Created
     */
    @Test
    public void should_return_success_when_register_given_validate_user() throws Exception {
        UserDTO validUser = new UserDTO();
        validUser.setPhone("13800138000");
        validUser.setPassword("Password123");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
    }
    /**
     * 测试场景：注册时手机号无效
     * 预期结果：返回400 Bad Request
     */
    @Test
    public void should_return_bad_request_when_register_given_null_phone() throws Exception {
        UserDTO invalidUser = new UserDTO();
        invalidUser.setPhone("");
        invalidUser.setPassword("Password123");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
    /**
     * 测试场景：注册时密码无效
     * 预期结果：返回400 Bad Request
     */
    @Test
    public void should_return_bad_request_when_register_given_null_password() throws Exception {
        UserDTO invalidUser = new UserDTO();
        invalidUser.setPhone("13800138000");
        invalidUser.setPassword("");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
    /**
     * 测试场景：注册时手机号已存在
     * 预期结果：返回400 Bad Request
     */
    @Test
    public void should_return_bad_request_when_register_given_existing_phone() throws Exception {
        UserDTO invalidUser = new UserDTO();
        invalidUser.setPhone("13800138000");
        invalidUser.setPassword("Password123");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("phone already exists"));
    }
}
