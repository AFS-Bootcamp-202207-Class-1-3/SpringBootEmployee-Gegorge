package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoSuchEmployeeException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeServiceImpl implements IEmployee{
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAllEmployee() {
        return employeeRepository.findAllEmployee();
    }
    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findEmployeesByGender(gender);
    }

    public Integer addEmployee(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }


    public Employee updateEmployee(Integer id, Employee updateEmployee) {
        return employeeRepository.updateEmployeeById(id, updateEmployee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.removeById(id);
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employeeRepository.employees.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void clearAll() {
        employeeRepository.employees.clear();
    }
}
