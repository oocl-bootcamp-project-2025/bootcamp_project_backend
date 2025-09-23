package com.oocl.bootcampbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // 注入 BCrypt 密码编码器（Spring Security 推荐）
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 可指定“工作因子”
        return new BCryptPasswordEncoder(10);
    }
}
