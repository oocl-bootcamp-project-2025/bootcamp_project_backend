package com.oocl.bootcampbackend.controller;

import com.oocl.bootcampbackend.controller.dto.UserDTO;
import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> saveUser(@RequestBody User user){
        userService.validateUser(user);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/login")
    public ResponseEntity<Void> loginUser(@RequestBody UserDTO userDTO){
        userService.login(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO){
        userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
