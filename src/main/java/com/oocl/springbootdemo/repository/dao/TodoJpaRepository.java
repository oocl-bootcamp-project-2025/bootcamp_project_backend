package com.oocl.springbootdemo.repository.dao;

import com.oocl.springbootdemo.object.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<Todo, Long> {
}
