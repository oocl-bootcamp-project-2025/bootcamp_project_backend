package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    private final static List<Company> companies = new ArrayList<>();
    private static long id = 0;

    public static void clear() {
        companies.clear();
        id = 0;
    }

    public Map<String, Long> create(Company company) {
        company.setId(++id); //variable
        companies.add(company);
        return Map.of("id", company.getId());
    }

    public Company update(long id, Company updateCompany) {
        Company target = companies.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .orElse(null);

        if (target != null) {
            target.setName(updateCompany.getName());
        }

        return target;
    }

    public Company get(long id) {
        return companies.stream().filter(company -> company.getId()==id).findFirst().orElse(null);
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
