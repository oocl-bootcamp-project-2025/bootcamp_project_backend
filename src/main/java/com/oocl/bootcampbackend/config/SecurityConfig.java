package com.oocl.bootcampbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 配置接口访问权限（使用新API）
                .authorizeHttpRequests(auth -> auth
                        // 仅限制 GET 方法的 /trips 接口需要登录（非过时写法）
                        .requestMatchers(HttpMethod.GET, "/trips").authenticated()
                        // 其他所有请求全部放行
                        .anyRequest().permitAll()
                )
                // 禁用默认表单登录
                .formLogin(form -> form.disable())
                // 禁用HTTP基本认证
                .httpBasic(httpBasic -> httpBasic.disable())
                // 关闭CSRF（开发环境）
                .csrf(csrf -> csrf.disable())
                // 无状态会话（适合JWT）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
