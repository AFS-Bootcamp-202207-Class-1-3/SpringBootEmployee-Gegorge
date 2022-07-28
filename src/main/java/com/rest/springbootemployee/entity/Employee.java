package com.rest.springbootemployee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private String gender;
    private int salary;
    private int companyId;



    public Employee(int id, String name, int age, String gender, int salary) {
        this(id, name, age, gender, salary, 100);
    }

    public Employee() {
    }

    public Employee(int id, String name, int age, String gender, int salary, int companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getSalary() {
        return salary;
    }


    public Employee merge(Employee employee) {
        this.name = employee.getName();
        this.age = employee.getAge();
        this.salary = employee.getSalary();
        this.gender = employee.getGender();
        return this;
    }
}
