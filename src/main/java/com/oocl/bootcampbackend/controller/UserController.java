package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.UserDTO;
import com.oocl.bootcampbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserDTO userDTO){
        String token = userService.login(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/users/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserDTO userDTO){
        userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
