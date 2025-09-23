package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    public User findByPhone(String phone);
    public List<User> findAll();
    public User findById(Long id);
    public void save(User user);
    public boolean isExistingPhone(String phone);
    public String findPasswordByPhone(String phone);
}
