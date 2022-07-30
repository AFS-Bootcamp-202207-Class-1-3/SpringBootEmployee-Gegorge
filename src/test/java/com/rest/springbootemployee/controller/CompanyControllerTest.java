package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.CompanyServiceImpl;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CompanyControllerTest {
    @Autowired
    MockMvc client;

    @Autowired
    JpaEmployeeRepository jpaEmployeeRepository;

    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    JpaCompanyRepository jpaCompanyRepository;

    private int initCompanyId;


    @BeforeEach
//    @Transactional
    void setupDB() {
        jpaCompanyRepository.deleteAll();
        jpaEmployeeRepository.deleteAll();
//        jpaCompanyRepository.truncateMyTable();
        Company company = new Company();
        company.setCompanyName("OOCL");
        Company initCompany = jpaCompanyRepository.save(company);
        initCompanyId = initCompany.getId();
    }

//    @AfterEach
//    void clear() {
//        jpaCompanyRepository.deleteAll();
//        jpaEmployeeRepository.deleteAll();
//    }

    @Test
    void should_get_all_companies_when_perform_given_companies() throws Exception {
        //given

        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(initCompanyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOCL"));
    }

    @Test
    void should_create_new_company_when_perform_post_given_new_company() throws Exception {
        //given
        String newCompany =  "{\n" +
                "        \"id\": 1,\n" +
                "        \"companyName\": \"OOCL\",\n" +
                "        \"employees\": []\n" +
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
    }

    @Test
    void should_get_company_by_id_when_perform_given_company_id() throws Exception {
        //given
       jpaCompanyRepository.save(new Company(22,"IQAX", Collections.emptyList()));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",initCompanyId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("OOCL"));
    }

    @Test
    void should_throw_no_such_company_exception_when_perform_given_wrong_company_id() throws Exception {
        //given
        jpaCompanyRepository.deleteAll();
        Company company = jpaCompanyRepository.save(new Company(1, "OOCL", Collections.emptyList()));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",company.getId() + 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void should_get_companies_by_page_when_perform_given_page_and_pageSize() throws Exception {
        //given
        jpaCompanyRepository.save(new Company(1, "CargoSmart", Collections.emptyList()));
        jpaCompanyRepository.save(new Company(2, "IQAX", Collections.emptyList()));
        Company company = jpaCompanyRepository.save(new Company(3, "OOIL", Collections.emptyList()));


        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies")
                        .queryParam("page", "2").queryParam("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOIL"));

    }

    @Test
    void should_update_employee_salary_to_300_by_id_when_perform_given_update_employee() throws Exception {
        //given
        String newCompany = "{\n" +
                "        \"id\": 2,\n" +
                "        \"companyName\": \"IQAX\",\n" +
                "        \"employees\": []\n" +
                "    }";
        //when & then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}",initCompanyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("IQAX"));
    }

    @Test
    void should_delete_company_by_id_when_perform_given_delete_company_id() throws Exception {
        //given


        //when & then
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", initCompanyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    void should_get_all_employees_when_perform_given_company_id() throws Exception {
        //given
        jpaEmployeeRepository.save(new Employee(1, "George1", 18, "male", 188, initCompanyId));

        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees",initCompanyId)
                        .queryParam("gender", "female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(188));
    }

}
