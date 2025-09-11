package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.UpdateCompanyRequest;
import com.oocl.springbootdemo.object.UpdateEmployeeRequest;

import java.util.List;

public interface CompanyRepository {
    void clearAll();

    Company save(Company company);

    Company findById(long id);

    Company update(Company company, UpdateCompanyRequest updateCompanyRequest);

    List<Company> query(Integer page, Integer size);

    void delete(Company target);
}
