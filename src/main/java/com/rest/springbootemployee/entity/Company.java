package com.rest.springbootemployee.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String companyName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
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

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void merge(Company company) {
        if (company.getCompanyName() != null) {
            this.setCompanyName(company.getCompanyName());
        }
        if (company.getEmployees() != null) {
            this.employees = company.getEmployees();
        }
    }
}
