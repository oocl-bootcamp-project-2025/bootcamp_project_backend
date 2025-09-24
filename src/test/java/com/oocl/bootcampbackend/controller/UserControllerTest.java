package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.controller.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

    /**
     * 测试场景：登录时手机号为空
     * 预期结果：返回400 Bad Request
     */
    @Test
    public void should_return_bad_request_when_login_given_empty_phone() throws Exception {
        UserDTO invalidUser = new UserDTO();
        invalidUser.setPhone("");
        invalidUser.setPassword("Password123");
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    /**
     * 测试场景： 登录时密码不匹配
     * 预期结果：返回400 Bad Request
     */
    @Test
    public void should_return_bad_request_when_login_given_wrong_password() throws Exception {
        UserDTO validUser = new UserDTO();
        validUser.setPhone("13800138000");
        validUser.setPassword("Password123");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());

        validUser.setPassword("WrongPassword");
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("password not match"));
    }

    /**
     * 测试场景： 正常登录
     * 预期结果：返回201
     */
//    @Test
//    public void should_return_201_when_login_given_valid_user() throws Exception {
//        UserDTO validUser = new UserDTO();
//        validUser.setPhone("13800138000");
//        validUser.setPassword("Password123");
//        mockMvc.perform(post("/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validUser)))
//                .andExpect(status().isCreated());
//        mockMvc.perform(post("/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validUser)))
//                .andExpect(status().isCreated());
//    }
    /**
     * 测试场景： 登录时手机号不存在
     * 预期结果：返回400 Bad Request
     */
    @Test
    public void should_return_400_when_login_given_invalid_user() throws Exception {
        UserDTO validUser = new UserDTO();
        validUser.setPhone("13800138001");
        validUser.setPassword("Password123");
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
        validUser.setPhone("13800138002");
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("user not exists"));
    }



}
