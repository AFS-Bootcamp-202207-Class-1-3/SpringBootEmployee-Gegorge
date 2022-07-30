package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.dto.EmployeeRequest;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.mapper.EmployeeMapper;
import com.rest.springbootemployee.service.EmployeeServiceImpl;
import com.rest.springbootemployee.vo.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;
    @Autowired
    private EmployeeMapper employeeMapper;
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeServiceImpl.findAllEmployee();
    }
    @GetMapping(path = "/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeServiceImpl.findEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employeeServiceImpl.findEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);
        Employee addedEmployee = employeeServiceImpl.addEmployee(employee);
        return employeeMapper.toResponse(addedEmployee);
    }

    @PutMapping(path = "/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeServiceImpl.updateEmployee(id, employee);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeServiceImpl.deleteEmployeeById(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam int page, int pageSize) {
        return employeeServiceImpl.findEmployeeByPage(page, pageSize);
    }
}
