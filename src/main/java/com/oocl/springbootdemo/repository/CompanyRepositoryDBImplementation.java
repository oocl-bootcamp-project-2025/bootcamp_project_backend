package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.UpdateCompanyRequest;
import com.oocl.springbootdemo.repository.dao.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepositoryDBImplementation implements CompanyRepository{

    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    public void clearAll() {
        companyJpaRepository.deleteAll();
    }

    public Company save(Company company) {
        return companyJpaRepository.save(company);
    }

    public Company findById(long id) {
        return companyJpaRepository.findById(id).orElse(null);
    }

    public Company update(Company company, UpdateCompanyRequest updateCompanyRequest) {
        company.setName(updateCompanyRequest.getName());
        company.setEmployees(updateCompanyRequest.getEmployees());
        return companyJpaRepository.save(company);
    }

    public List<Company> query(Integer page, Integer size) {
        if (page != null && size != null) {
            return companyJpaRepository.findAll(PageRequest.of(page-1, size)).toList();
        }
        return companyJpaRepository.findAll();
    }

    public void delete(Company target) {
        companyJpaRepository.delete(target);
    }
}
