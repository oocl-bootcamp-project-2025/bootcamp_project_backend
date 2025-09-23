package com.oocl.bootcampbackend.repository.impl;

import com.oocl.bootcampbackend.entity.User;
import com.oocl.bootcampbackend.repository.UserRepository;
import com.oocl.bootcampbackend.repository.dao.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Override
    public User findByPhone(String phone) {
        return userJpaRepository.findByPhone(phone);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public boolean isExistingPhone(String phone) {
        return userJpaRepository.existsByPhone(phone);
    }

    @Override
    public String findPasswordByPhone(String phone) {
        User byPhone = userJpaRepository.findByPhone(phone);;
        return byPhone.getPassword();
    }
}
