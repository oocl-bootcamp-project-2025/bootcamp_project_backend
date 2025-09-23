package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.controller.dto.UserDTO;
import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.exception.ErrorPasswordException;
import com.oocl.bootcampbackend.exception.ExistingUserException;
import com.oocl.bootcampbackend.exception.NotExistingUserException;
import com.oocl.bootcampbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void login(UserDTO userDTO) {
        String phone = userDTO.getPhone();
        String password = userDTO.getPassword();
        if (!userRepository.isExistingPhone(phone))
        {
            throw new NotExistingUserException("user not exists");
        }
        if (!passwordEncoder.matches(password, userRepository.findPasswordByPhone(phone))) {
            throw new ErrorPasswordException("password not match");
        }
    }

    public void register(UserDTO userDTO) {
        if (userRepository.isExistingPhone(userDTO.getPhone())) {
            throw new ExistingUserException("phone already exists");
        }

        // 密码加密：使用 BCrypt 加密原始密码
        String rawPassword = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setPhone(userDTO.getPhone());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
