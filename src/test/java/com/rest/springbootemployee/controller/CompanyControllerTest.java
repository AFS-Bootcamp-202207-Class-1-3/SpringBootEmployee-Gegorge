package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureMockMvc
@SpringBootTest
public class CompanyControllerTest {
    @Autowired
    MockMvc client;

    @Autowired
    CompanyServiceImpl companyService;

    @BeforeEach
    void cleanDB() {
        companyService.clearAll();
    }

    @Test
    void should_get_all_companies_when_perform_given_employees() throws Exception {
        //given
        Employee employee = new Employee(1, "George", 18, "male", 190);
        companyService.addCompany(new Company(1,"OOCL", Arrays.asList(employee)));

        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOCL"));
    }

    @Test
    void should_create_new_company_when_perform_post_given_new_company() throws Exception {
        //given
        String newCompany = "    {\n" +
                "        \"id\": 1,\n" +
                "        \"companyName\": \"OOCL\",\n" +
                "        \"employees\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"George1\",\n" +
                "                \"age\": 18,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 180\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"George2\",\n" +
                "                \"age\": 18,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 180\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"George3\",\n" +
                "                \"age\": 18,\n" +
                "                \"gender\": \"male\",\n" +
                "                \"salary\": 180\n" +
                "            }\n" +
                "        ]\n" +
                "    }";

        //when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //should
        List<Company> companies = companyService.findAllCompanies();
        assertThat(companies.size(), equalTo(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("OOCL"));
        assertThat(companies.get(0).getEmployees().size(), equalTo(3));
    }

    @Test
    void should_get_company_by_id_when_perform_given_company_id() throws Exception {
        //given
        Employee employee = new Employee(1, "George", 18, "male", 190);
        int id1 = companyService.addCompany(new Company(1, "OOCL", Arrays.asList(employee)));
        int id2 = companyService.addCompany(new Company(2, "IQAX", Arrays.asList(employee)));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",id2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("IQAX"));
    }

    @Test
    void should_throw_no_such_company_exception_when_perform_given_wrong_company_id() throws Exception {
        //given
        Employee employee = new Employee(1, "George", 18, "male", 190);
        int id = companyService.addCompany(new Company(1, "OOCL", Arrays.asList(employee)));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",2))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
