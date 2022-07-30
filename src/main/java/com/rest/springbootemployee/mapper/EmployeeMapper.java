package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.dto.EmployeeRequest;
import com.rest.springbootemployee.vo.EmployeeResponse;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee convertToEntity(EmployeeRequest request) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(request, employee);
        return employee;
    }

    public EmployeeResponse convertToVO(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(employee, response);
        return response;
    }
}
