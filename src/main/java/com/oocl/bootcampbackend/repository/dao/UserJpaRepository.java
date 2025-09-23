package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByPhone(String phone);
    boolean existsByPhone(String phone);
    String findPasswordByPhone(String phone);
}
