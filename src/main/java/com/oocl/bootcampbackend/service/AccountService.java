package com.oocl.bootcampbackend.service;

import com.oocl.bootcampbackend.controller.dto.AccountDTO;
import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.exception.ErrorPasswordException;
import com.oocl.bootcampbackend.exception.ExistingAccountException;
import com.oocl.bootcampbackend.exception.NotExistingAccountException;
import com.oocl.bootcampbackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.oocl.bootcampbackend.util.JwtUtil.generateToken;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(AccountDTO accountDTO) {
        String phone = accountDTO.getPhone();
        String password = accountDTO.getPassword();
        if (!accountRepository.isExistingPhone(phone))
        {
            throw new NotExistingAccountException("user not exists");
        }
        if (!passwordEncoder.matches(password, accountRepository.findPasswordByPhone(phone))) {
            throw new ErrorPasswordException("password not match");
        }
        // 登录成功，生成JWT token
        return generateToken(phone);
    }

    public void register(AccountDTO accountDTO) {
        if (accountRepository.isExistingPhone(accountDTO.getPhone())) {
            throw new ExistingAccountException("phone already exists");
        }

        // 密码加密：使用 BCrypt 加密原始密码
        String rawPassword = accountDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Account account = new Account();
        account.setPhone(accountDTO.getPhone());
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }
}
