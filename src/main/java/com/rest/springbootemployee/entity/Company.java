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

    public void updateCompanyName() {
        this.setCompanyName("OOIL");
    }
}
