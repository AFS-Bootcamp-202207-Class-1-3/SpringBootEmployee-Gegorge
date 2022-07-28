package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoSuchEmployeeException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    public List<Employee> findAllEmployee() {
        return jpaEmployeeRepository.findAll();
    }
    public Employee findEmployeeById(Integer id) {
        return jpaEmployeeRepository.findById(id).orElseThrow(NoSuchEmployeeException::new);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return jpaEmployeeRepository.findByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        return jpaEmployeeRepository.save(employee);
    }


    public Employee updateEmployee(Integer id, Employee updateEmployee) {
        Employee employee = jpaEmployeeRepository.findById(id).orElseThrow(NoSuchEmployeeException::new);
        employee.merge(updateEmployee);
        return jpaEmployeeRepository.saveAndFlush(employee);
    }

    public void deleteEmployeeById(Integer id) {
        jpaEmployeeRepository.deleteById(id);
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return jpaEmployeeRepository.findAll(pageRequest).toList();
    }

}
