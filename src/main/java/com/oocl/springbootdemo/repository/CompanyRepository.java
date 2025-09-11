package com.oocl.springbootdemo.repository;

import com.oocl.springbootdemo.object.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private final static List<Company> companies = new ArrayList<>();
    private static long id = 0;

    public static void clear() {
        companies.clear();
        id = 0;
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
        return target;
    }

    public List<Company> query(Integer page, Integer size) {
        if (page != null && size != null) {
            return companies.subList((page - 1) * size, (page - 1) * size + size);
        }
        return companies;
    }

    public Company delete(Company target) {
        companies.remove(target);
        return null;
    }
}
