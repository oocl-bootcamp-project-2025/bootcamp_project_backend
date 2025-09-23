package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.service.AccountService;
import com.oocl.bootcampbackend.service.ViewpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.register(account));
    }

    @PostMapping("/login")
    public ResponseEntity<String> getAreas(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.login(account));
    }
}
