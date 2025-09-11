package com.oocl.springbootdemo.object;

import java.util.ArrayList;
import java.util.List;

public class UpdateCompanyRequest {
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
