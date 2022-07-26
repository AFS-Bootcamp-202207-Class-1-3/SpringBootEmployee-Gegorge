package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees;

    public EmployeeRepository() {
        this.employees = Arrays.asList(new Employee(1,"George1",18,"male",180),
                new Employee(2,"George2",18,"male",180),
                new Employee(3,"George3",18,"male",180),
                new Employee(4,"George4",18,"male",180),
                new Employee(5,"George5",18,"male",180));
    }

    public List<Employee> findAllEmployee() {
        return employees;
    }


}
