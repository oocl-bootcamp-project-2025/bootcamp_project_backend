package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Map<String, Long> create(Company company) {
        companyRepository.save(company);
        return Map.of("id", company.getId());
    }

    public Company update(long id, Company updateCompany) {
        Company foundcompany = get(id);
        if (foundcompany != null) {
            return companyRepository.update(foundcompany, updateCompany);
        }
        return null;
    }

    public Company get(long id) {
        return companyRepository.findById(id);
    }

    public List<Company> query(Integer page, Integer size) {
        return companyRepository.query(page, size);
    }

    public void delete(long id) {
        companyRepository.delete(id);
    }
}
