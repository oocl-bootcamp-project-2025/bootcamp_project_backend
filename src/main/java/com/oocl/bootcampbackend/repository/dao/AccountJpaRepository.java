package com.oocl.bootcampbackend.repository.dao;

import com.oocl.bootcampbackend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {
    Account findByPhone(String phone);
    boolean existsByPhone(String phone);
}
