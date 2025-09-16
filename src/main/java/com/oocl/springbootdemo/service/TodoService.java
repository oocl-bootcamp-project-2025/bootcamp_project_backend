package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

//    public Company create(Company company) {
//        return companyRepository.save(company);
//    }

//    public Company get(long id) {
//        Company foundCompany = companyRepository.findById(id);
//        if (foundCompany == null) {
//            throw new CompanyNotFoundException();
//        }
//        return foundCompany;
//    }

    public List<Todo> query() {
        return todoRepository.query();
    }

//    public Company update(long id, UpdateCompanyRequest updateCompanyRequest) {
//        Company foundCompany = get(id);
//        return companyRepository.update(foundCompany, updateCompanyRequest);
//    }
//
//    public void delete(long id) {
//        Company foundCompany = get(id);
//        companyRepository.delete(foundCompany);
//    }
//
//    public void clear() {
//        companyRepository.clearAll();
//    }
}
