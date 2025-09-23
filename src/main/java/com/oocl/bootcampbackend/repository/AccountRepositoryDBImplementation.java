package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Account;
import com.oocl.bootcampbackend.entity.Viewpoint;
import com.oocl.bootcampbackend.repository.dao.AccountJpaRepository;
import com.oocl.bootcampbackend.repository.dao.ViewpointJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryDBImplementation implements AccountRepository {

    @Autowired
    private AccountJpaRepository accountJpaRepository;

    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    public Account findByPhone(String phone) {
        return accountJpaRepository.findByPhone(phone);
    }
}
