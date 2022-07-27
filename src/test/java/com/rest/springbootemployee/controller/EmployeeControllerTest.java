package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoSuchEmployeeException;
import com.rest.springbootemployee.service.EmployeeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    MockMvc client;

    @Autowired
    EmployeeImpl employeeImpl;

    @BeforeEach
    void cleanDB() {
        employeeImpl.clearAll();
    }

    @Test
    void should_get_all_employees_when_perform_given_employees() throws Exception {
        //given
        employeeImpl.addEmployee(new Employee(1,"George",18,"male",190));

        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_create_new_employee_when_perform_post_given_new_employee() throws Exception {
        //given
        String newEmployee = "       {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"George1\",\n" +
                "        \"age\": 18,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 180\n" +
                "    }";

        //when & then
        client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("George1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(180));

        //should
        List<Employee> employees = employeeImpl.findAllEmployee();
        assertThat(employees.size(), equalTo(1));
        assertThat(employees.get(0).getName(), equalTo("George1"));
        assertThat(employees.get(0).getGender(), equalTo("male"));
        assertThat(employees.get(0).getSalary(), equalTo(180));
        assertThat(employees.get(0).getAge(), equalTo(18));
    }

    @Test
    void should_get_employee_by_id_when_perform_given_employee_id() throws Exception {
        //given
        Employee employee = employeeImpl.addEmployee(new Employee(2, "George", 18, "male", 190));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}",employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("George"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(190));
    }

    @Test
    void should_throw_no_such_employee_exception_when_perform_given_wrong_employee_id() throws Exception {
        //given
        employeeImpl.addEmployee(new Employee(2,"George",18,"male",190));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void should_get_employee_by_page_when_perform_given_page_and_pageSize() throws Exception {
        //given
        employeeImpl.addEmployee(new Employee(1,"George1",18,"male",190));
        employeeImpl.addEmployee(new Employee(2,"George2",18,"male",190));
        employeeImpl.addEmployee(new Employee(3,"George3",18,"male",190));


        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees")
                .queryParam("page", "2").queryParam("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(190));

    }






}
