package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IEmployee {
    Employee findEmployeeById(Integer id);
    List<Employee> findEmployeesByGender(String gender);
    List<Employee> addEmployee(Employee employee);
    Integer generateEmployeeId();
    Employee updateEmployee(Integer id);
    void deleteEmployeeById(Integer id);
    List<Employee> findEmployeeByPage(int page, int pageSize);
}
