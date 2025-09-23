package com.oocl.bootcampbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.repository.AccountRepository;
import com.oocl.bootcampbackend.repository.dao.AccountJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountJpaRepository accountJpaRepository;


    Account createAccount(String phone, String password) {
        Account account = new Account();
        account.setPhone(phone);
        account.setPassword(password);
        return accountRepository.save(account);
    }

    @BeforeEach
    void reset() {
        accountJpaRepository.deleteAll();
    }

    @Test
    void should_get_message_when_register_given_phone_and_password() throws Exception {
        Account account = new Account();
        account.setPhone("12345678");
        account.setPassword("password");

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Register Success"));
    }

    @Test
    void should_get_message_when_login_given_phone_and_password() throws Exception {
        Account account = createAccount("12345678", "password");

        mockMvc.perform(post("/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Login Success"));
    }
}
