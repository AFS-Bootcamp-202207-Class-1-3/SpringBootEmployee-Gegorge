package com.rest.springbootemployee.dto;

import com.rest.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyRequest {
    private String companyName;
    List<Employee> employees;

    public CompanyRequest() {
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
