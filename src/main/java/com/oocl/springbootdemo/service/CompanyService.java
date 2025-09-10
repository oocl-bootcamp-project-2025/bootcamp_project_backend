package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.exception.*;
import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company get(long id) {
        Company foundCompany = companyRepository.findById(id);
        if (foundCompany == null) {
            throw new CompanyNotFoundException();
        }
        return foundCompany;
    }

    public List<Company> query(Integer page, Integer size) {
        return companyRepository.query(page, size);
    }

    public Company update(long id, Company updateCompany) {
        Company foundCompany = get(id);
        return companyRepository.update(foundCompany, updateCompany);
    }

    public Company delete(long id) {
        Company foundCompany = get(id);
        return companyRepository.delete(foundCompany);
    }
}
