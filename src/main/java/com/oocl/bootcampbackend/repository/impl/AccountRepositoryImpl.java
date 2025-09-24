package com.oocl.bootcampbackend.repository.impl;

import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.repository.AccountRepository;
import com.oocl.bootcampbackend.repository.dao.AccountJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountJpaRepository accountJpaRepository;
    @Override
    public Account findByPhone(String phone) {
        return accountJpaRepository.findByPhone(phone);
    }

    @Override
    public List<Account> findAll() {
        return accountJpaRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountJpaRepository.findById(id).get();
    }

    @Override
    public void save(Account account) {
        accountJpaRepository.save(account);
    }

    @Override
    public boolean isExistingPhone(String phone) {
        return accountJpaRepository.existsByPhone(phone);
    }

    @Override
    public String findPasswordByPhone(String phone) {
        Account byPhone = accountJpaRepository.findByPhone(phone);;
        return byPhone.getPassword();
    }
}
