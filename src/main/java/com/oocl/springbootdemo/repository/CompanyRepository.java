package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Company;

import java.util.List;

public interface CompanyRepository {
    void clearAll();

    Company save(Company company);

    Company findById(long id);

    Company update(Company target, Company updateCompany);

    List<Company> query(Integer page, Integer size);

    void delete(Company target);
}
