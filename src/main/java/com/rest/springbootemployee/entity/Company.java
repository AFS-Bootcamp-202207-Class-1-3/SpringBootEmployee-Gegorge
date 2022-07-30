package com.rest.springbootemployee.entity;

import javax.persistence.*;
import java.util.List;
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String companyName;
    @OneToMany(orphanRemoval = true)
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void merge(Company company) {
        this.setCompanyName(company.getCompanyName());
        this.employees = company.getEmployees();
    }
}
