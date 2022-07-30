package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    JpaEmployeeRepository jpaEmployeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    void should_return_all_employee_when_get_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90, 100));
        given(jpaEmployeeRepository.findAll()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.findAllEmployee();

        //then
        assertThat(actualEmployees.get(0).getName(), equalTo("George"));
        assertThat(actualEmployees.get(0).getAge(), equalTo(23));
    }

    @Test
    void should_return_employee_when_find_by_id_given_employee_id() {
        //given
        Employee employee = new Employee(1, "George", 23, "male", 90, 100);
        int id = 1;
        given(jpaEmployeeRepository.findById(id)).willReturn(Optional.of(employee));

        //when
        Employee actualEmployee = employeeService.findEmployeeById(id);

        //then
        assertThat(actualEmployee.getName(), equalTo("George"));
    }

    @Test
    void should_return_updated_employee_when_update_given_employee() {
        //given
        int newSalary = 100;
        Employee originalEmployee = new Employee(1, "Miky", 24, "female", 90, 100);
        Employee updateEmployee = new Employee(2, "Miky", 24, "female", newSalary, 100);
        given(jpaEmployeeRepository.findById(1)).willReturn(Optional.of(originalEmployee));

        //when
        Employee employee = employeeService.updateEmployee(1, updateEmployee);

        //then
        verify(jpaEmployeeRepository).saveAndFlush(originalEmployee);
    }

    @Test
    void should_return_employees_by_gender_when_find_employee_by_gender_given_gender() {
        //given
        String gender = "male";
        List<Employee> employees = Arrays.asList(
                new Employee(1, "George1", 23, "male", 90, 100),
                new Employee(2, "George2", 23, "male", 90, 100),
                new Employee(3, "George3", 23, "male", 90, 100)
        );
        given(jpaEmployeeRepository.findByGender(gender)).willReturn(employees);

        //when
        List<Employee> employeesByGender = employeeService.findEmployeesByGender(gender);

        //then
        assertThat(employeesByGender.size(), equalTo(3));
    }

    @Test
    void should_return_id_when_add_employee_given_employee() {
        //given
        Employee employee = new Employee(1, "George1", 23, "male", 90, 100);
        given(jpaEmployeeRepository.save(employee)).willReturn(employee);

        //when
        Employee employee1 = employeeService.addEmployee(employee);

        //then
        assertThat(employee1.getId(), equalTo(1));
    }

    @Test
    void should_remove_employee_when_delete_employee_given_employee_id() {
        //given

        //when
        employeeService.deleteEmployeeById(1);
        //then
        verify(jpaEmployeeRepository, times(1)).deleteById(1);
    }

    @Test
    void should_return_employees_by_page_when_get_employee_given_page_and_pageSize() {
        //given
        List<Employee> employees = Arrays.asList(
                new Employee(1, "George1", 23, "male", 90, 100),
                new Employee(2, "George2", 23, "male", 90, 100),
                new Employee(3, "George3", 23, "male", 90, 100)
        );
        PageRequest pageRequest = PageRequest.of(2, 2);
        Page<Employee> employeePage = new PageImpl<>(employees, pageRequest, employees.size());
        given(jpaEmployeeRepository.findAll(pageRequest)).willReturn(employeePage);
        //when
        employeeService.findEmployeeByPage(3, 2);
        //then
        verify(jpaEmployeeRepository, times(1)).findAll(pageRequest);
    }


}
