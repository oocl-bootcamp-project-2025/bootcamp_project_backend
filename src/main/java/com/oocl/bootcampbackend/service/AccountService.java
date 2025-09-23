package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.repository.AccountRepository;
import com.oocl.bootcampbackend.repository.ViewpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public String register(Account account) {
        if (accountRepository.findByPhone(account.getPhone()) == null) {
            accountRepository.save(account);
            return "Register Success";
        }
        return "Register Fail";
    }

    public String login(Account account) {
        if (accountRepository.findByPhone(account.getPhone())!=null && accountRepository.findByPhone(account.getPhone()).getPassword().equals(account.getPassword())) {
            return "Login Success";
        }
        return "Login Fail";
    }
}
