package com.rest.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.dto.EmployeeRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeControllerTest {
    @Autowired
    MockMvc client;

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    JpaEmployeeRepository jpaEmployeeRepository;
    @Autowired
    JpaCompanyRepository jpaCompanyRepository;

    private Integer initCompanyId;


    @BeforeEach
    void setupDB() {
        jpaEmployeeRepository.deleteAll();
        Company company = new Company();
        company.setCompanyName("OOCL");
        Company initCompany = jpaCompanyRepository.save(company);
        initCompanyId = initCompany.getId();
    }

    @Test
    void should_get_all_employees_when_perform_given_employees() throws Exception {
        //given
        employeeServiceImpl.addEmployee(new Employee(1, "George", 18, "male", 190, initCompanyId));

        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_create_new_employee_when_perform_post_given_new_employee() throws Exception {
        //given
        EmployeeRequest request = new EmployeeRequest();
        request.setName("George1");
        request.setAge(18);
        request.setSalary(180);
        request.setGender("male");
        request.setCompanyId(initCompanyId);
        String newEmployee = new ObjectMapper().writeValueAsString(request);

        //when & then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //should
        List<Employee> employees = employeeServiceImpl.findAllEmployee();
        assertThat(employees.size(), equalTo(1));
        assertThat(employees.get(0).getName(), equalTo("George1"));
        assertThat(employees.get(0).getGender(), equalTo("male"));
        assertThat(employees.get(0).getSalary(), equalTo(180));
        assertThat(employees.get(0).getAge(), equalTo(18));
    }

    @Test
    void should_get_employee_by_id_when_perform_given_employee_id() throws Exception {
        //given
        Employee employee = employeeServiceImpl.addEmployee(
                new Employee(2, "George", 18, "male", 190, initCompanyId));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("George"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(190));
    }

    @Test
    void should_throw_no_found_employee_exception_when_perform_given_wrong_employee_id() throws Exception {
        //given
        employeeServiceImpl.addEmployee(
                new Employee(2, "George", 18, "male", 190, initCompanyId));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", 3))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(exception -> assertThat("Not Found: employee",
                        equalTo(Objects.requireNonNull(exception.getResolvedException()).getMessage())));
    }


    @Test
    void should_get_employee_by_page_when_perform_given_page_and_pageSize() throws Exception {
        //given
        Employee employee1 = employeeServiceImpl.addEmployee(
                new Employee(1, "George1", 18, "male", 190, initCompanyId));
        Employee employee2 = employeeServiceImpl.addEmployee(
                new Employee(2, "George2", 18, "male", 190, initCompanyId));
        Employee employee3 = employeeServiceImpl.addEmployee(
                new Employee(3, "George3", 18, "male", 190, initCompanyId));


        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .queryParam("page", "2").queryParam("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(190));

    }

    @Test
    void should_update_employee_salary_to_300_by_id_when_perform_given_update_employee() throws Exception {
        //given
        Employee employee = employeeServiceImpl.addEmployee(
                new Employee(1, "George", 18, "male", 190, initCompanyId));
        EmployeeRequest request = new EmployeeRequest();
        request.setName("George111");
        request.setAge(111);
        request.setSalary(222);
        String requestAsString = new ObjectMapper().writeValueAsString(request);
        //when & then
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("George111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(222));
    }

    @Test
    void should_delete_employee_by_id_when_perform_given_delete_employee() throws Exception {
        //given
        Employee employee = employeeServiceImpl
                .addEmployee(new Employee(1, "George1", 18, "male", 190, initCompanyId));

        //when & then
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void should_get_employee_by_gender_when_perform_given_gender() throws Exception {
        //given
        employeeServiceImpl.addEmployee(new Employee(1, "George1", 18, "male", 180, initCompanyId));
        employeeServiceImpl.addEmployee(new Employee(2, "George2", 18, "female", 1801, initCompanyId));

        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .queryParam("gender", "female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(1801));
    }

}
