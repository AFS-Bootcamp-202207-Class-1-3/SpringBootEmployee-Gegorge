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
        return employeeRepository.employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Employee addEmployee = new Employee(generateEmployeeId(),
                employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
        employeeRepository.employees.add(addEmployee);
        return addEmployee;
    }

    public Integer generateEmployeeId() {
        return employeeRepository.employees.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0) + 1;
    }

    public Employee updateEmployee(Integer id, Employee updateEmployee) {
        return employeeRepository.updateEmployeeById(id, updateEmployee);
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

    public void clearAll() {
        employeeRepository.employees.clear();
    }
}
