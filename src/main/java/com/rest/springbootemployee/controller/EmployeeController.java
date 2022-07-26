package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployee();
    }
    @GetMapping(path = "/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeRepository.findEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employeeRepository.findEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> addEmployee(@RequestBody Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    @PutMapping(path = "/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id) {
        return employeeRepository.updateEmployee(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeRepository.deleteEmployeeById(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam int page, int pageSize) {
        return employeeRepository.findEmployeeByPage(page, pageSize);
    }
}
