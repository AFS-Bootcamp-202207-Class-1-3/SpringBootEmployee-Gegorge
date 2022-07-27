package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Test
    void should_return_all_employee_when_get_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90));
        given(employeeRepository.findAllEmployee()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.findAllEmployee();

        //then
        assertThat(actualEmployees.get(0).getName(), equalTo("George"));
        assertThat(actualEmployees.get(0).getAge(), equalTo(23));
    }

    @Test
    void should_return_employee_when_find_by_id_given_employee_id() {
        //given
        Employee employee = new Employee(1, "George", 23, "male", 90);
        int id = 1;
        given(employeeRepository.findEmployeeById(id)).willReturn(employee);

        //when
        Employee actualEmployee = employeeService.findEmployeeById(id);

        //then
        assertThat(actualEmployee.getName(), equalTo("George"));
    }

    @Test
    void should_return_updated_employee_when_update_given_employee() {
        //given
        int newSalary = 100;
        Employee originalEmployee = new Employee(1, "Miky", 24, "female", 90);
        Employee updateEmployee = new Employee(2, "Miky", 24, "female", newSalary);
        given(employeeRepository.findEmployeeById(1)).willReturn(originalEmployee);
        given(employeeRepository.updateEmployeeById(1, updateEmployee)).willCallRealMethod();

        //when
        Employee employee = employeeService.updateEmployee(1, updateEmployee);

        //then
        verify(employeeRepository).updateEmployeeById(1, updateEmployee);
    }
    
    @Test
    void should_return_employees_by_gender_when_find_employee_by_gender_given_gender() {
        //given
        String gender = "male";
        List<Employee> employees = Arrays.asList(
                new Employee(1, "George1", 23, "male", 90),
                new Employee(2, "George2", 23, "male", 90),
                new Employee(3, "George3", 23, "male", 90)
        );
        given(employeeRepository.findEmployeesByGender(gender)).willReturn(employees);
        
        //when
        List<Employee> employeesByGender = employeeService.findEmployeesByGender(gender);

        //then
        assertThat(employeesByGender.size(), equalTo(3));
    }

    @Test
    void should_return_id_when_add_employee_given_employee() {
        //given
        String gender = "male";

        Employee employee = new Employee(1, "George1", 23, "male", 90);
        given(employeeRepository.addEmployee(employee)).willReturn(1);

        //when
        Integer id = employeeService.addEmployee(employee);

        //then
        assertThat(id, equalTo(1));
    }

}
