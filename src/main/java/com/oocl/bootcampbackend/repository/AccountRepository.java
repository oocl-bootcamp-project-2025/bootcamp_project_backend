package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository {
    public Account findByPhone(String phone);
    public List<Account> findAll();
    public Account findById(Long id);
    public void save(Account account);
    public boolean isExistingPhone(String phone);
    public String findPasswordByPhone(String phone);
}
