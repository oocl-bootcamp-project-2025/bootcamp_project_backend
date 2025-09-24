package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.AccountDTO;
import com.oocl.bootcampbackend.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid AccountDTO accountDTO){
        String token = accountService.login(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/accounts/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid AccountDTO accountDTO){
        accountService.register(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
