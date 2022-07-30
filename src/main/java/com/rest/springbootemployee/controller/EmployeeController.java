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
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeServiceImpl.findAllEmployee();
        return employeeMapper.convertToVOs(employees);
    }
    @GetMapping(path = "/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeServiceImpl.findEmployeeById(id);
        return employeeMapper.convertToVO(employee);
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeesByGender(@RequestParam String gender) {
        List<Employee> employees = employeeServiceImpl.findEmployeesByGender(gender);
        return employeeMapper.convertToVOs(employees);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest request) {
        Employee employee = employeeMapper.convertToEntity(request);
        Employee addedEmployee = employeeServiceImpl.addEmployee(employee);
        return employeeMapper.convertToVO(addedEmployee);
    }

    @PutMapping(path = "/{id}")
    public EmployeeResponse updateEmployeeById(@PathVariable Integer id, @RequestBody EmployeeRequest request) {
        Employee updatedEmployee = employeeServiceImpl.updateEmployee(id, employeeMapper.convertToEntity(request));
        return employeeMapper.convertToVO(updatedEmployee);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeServiceImpl.deleteEmployeeById(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<EmployeeResponse> getEmployeeByPage(@RequestParam int page, int pageSize) {
        List<Employee> employees = employeeServiceImpl.findEmployeeByPage(page, pageSize);
        return employeeMapper.convertToVOs(employees);
    }
}
