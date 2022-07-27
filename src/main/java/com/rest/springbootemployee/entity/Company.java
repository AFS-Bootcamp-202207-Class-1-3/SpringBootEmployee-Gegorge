package com.rest.springbootemployee.entity;

import java.util.List;

public class Company {
    private int id;
    private String companyName;
    List<Employee> employees;

    public Company(int id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company updateCompanyName(Company company) {
        this.setCompanyName(company.getCompanyName());
        this.employees = company.getEmployees();
        return this;
    }
}
