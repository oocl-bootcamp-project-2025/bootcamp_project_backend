package com.oocl.bootcampbackend.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {
    Account findByPhone(String phone);
}
