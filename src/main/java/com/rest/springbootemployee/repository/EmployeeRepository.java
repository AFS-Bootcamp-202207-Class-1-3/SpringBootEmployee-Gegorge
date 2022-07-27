package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoSuchEmployeeException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    public final List<Employee> employees;

    public EmployeeRepository() {
        this.employees = new ArrayList<>();
        employees.add(new Employee(1,"George1",18,"male",180));
        employees.add(new Employee(2,"George2",18,"male",180));
        employees.add(new Employee(3,"George3",18,"male",180));
        employees.add(new Employee(4,"George4",18,"female",180));
        employees.add(new Employee(5,"George5",18,"female",180));
    }

    public List<Employee> findAllEmployee() {
        return employees;
    }

    public Employee findEmployeeById(Integer id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchEmployeeException::new);
    }

    public Employee updateEmployeeById(Integer id, Employee updateEmployee) {
         return findEmployeeById(id).merge(updateEmployee);
    }
}
