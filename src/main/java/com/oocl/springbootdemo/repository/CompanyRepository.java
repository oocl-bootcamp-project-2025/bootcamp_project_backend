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

    public void save(Company company) {
        company.setId(++id);
        companies.add(company);
    }

    public Company findById(long id) {
        return companies.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .orElse(null);
    }

    public Company update(Company target, Company updateCompany) {
        target.setName(updateCompany.getName());
        return target;
    }

    public List<Company> query(Integer page, Integer size) {
        if (page != null && size != null) {
            return companies.subList(page*size, page*size+size);
        }
        return companies;
    }

    public void delete(long id) {
        companies.remove(companies.stream().filter(company -> company.getId()==id).findFirst().orElse(null));
    }
}
