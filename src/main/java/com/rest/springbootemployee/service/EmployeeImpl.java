package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeImpl implements IEmployee{
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAllEmployee() {
        return employeeRepository.employees;
    }
    public Employee findEmployeeById(Integer id) {
        return employeeRepository.employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(NoClassDefFoundError::new);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> addEmployee(Employee employee) {
        employeeRepository.employees.add(new Employee(generateEmployeeId(),
                employee.getName(),employee.getAge(),employee.getGender(),employee.getSalary()));
        return employeeRepository.employees;
    }

    public Integer generateEmployeeId() {
        return employeeRepository.employees.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0) + 1;
    }

    public Employee updateEmployee(Integer id) {
        Employee updateEmployee = findEmployeeById(id);
        updateEmployee.updateSalary(300);
        return updateEmployee;
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.employees.remove(findEmployeeById(id));
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employeeRepository.employees.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
