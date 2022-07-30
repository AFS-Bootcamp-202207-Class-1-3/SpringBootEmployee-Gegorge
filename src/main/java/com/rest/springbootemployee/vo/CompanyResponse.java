package com.rest.springbootemployee.vo;

import com.rest.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyResponse {
    private int id;
    private String companyName;
    List<Employee> employees;

    public CompanyResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
