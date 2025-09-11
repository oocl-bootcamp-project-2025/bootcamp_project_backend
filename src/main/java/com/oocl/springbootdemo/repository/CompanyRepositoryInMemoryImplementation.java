package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepositoryInMemoryImplementation implements CompanyRepository {
    private final static List<Company> companies = new ArrayList<>();
    private static long id = 0;

    public void clearAll() {
        id = 0;
        companies.clear();
    }

    public Company save(Company company) {
        company.setId(++id);
        companies.add(company);
        return company;
    }

    public Company findById(long id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Company update(Company target, Company updateCompany) {
        target.setName(updateCompany.getName());
        target.setEmployees(updateCompany.getEmployees());
        return target;
    }

    public List<Company> query(Integer page, Integer size) {
        if (page != null && size != null) {
            return companies.subList((page-1)*size, Math.min((page-1)*size+size, companies.size()));
        }
        return companies;
    }

    public void delete(Company target) {
        companies.remove(target);
    }
}
