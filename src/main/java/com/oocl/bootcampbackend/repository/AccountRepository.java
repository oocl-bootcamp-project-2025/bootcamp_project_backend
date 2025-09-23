package com.oocl.bootcampbackend.repository;

import com.oocl.bootcampbackend.entity.Account;

public interface AccountRepository {
    Account save(Account account);
    Account findByPhone(String phone);
}
