package com.rest.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.CompanyServiceImpl;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @BeforeEach
    void setupDB() {
        jpaCompanyRepository.deleteAll();
        jpaEmployeeRepository.deleteAll();
    }

    @Test
    void should_get_all_companies_when_perform_given_companies() throws Exception {
        //given
        Company company1 = jpaCompanyRepository.save(new Company(1, "OOCL", Collections.emptyList()));
        Company company2 = jpaCompanyRepository.save(new Company(2, "CargoSmart", Collections.emptyList()));
        Company company3 = jpaCompanyRepository.save(new Company(3, "IQAX", Collections.emptyList()));
        jpaEmployeeRepository.save(new Employee(null, "George1", 18, "male", 333, company1.getId()));
        jpaEmployeeRepository.save(new Employee(null, "George2", 18, "male", 333, company2.getId()));
        jpaEmployeeRepository.save(new Employee(null, "George3", 18, "male", 333, company3.getId()));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(company1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(company2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(company3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOCL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName").value("CargoSmart"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].companyName").value("IQAX"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("George1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees[0].name").value("George2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].employees[0].name").value("George3"));
    }

    @Test
    void should_create_new_company_when_perform_post_given_new_company() throws Exception {
        //given
        CompanyRequest request = new CompanyRequest();
        request.setCompanyName("OOCL");
        request.setEmployees(Collections.emptyList());
        String requestAsString = new ObjectMapper().writeValueAsString(request);

        //when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //should
        List<Company> companies = companyService.findAllCompanies();
        assertThat(companies.size(), equalTo(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("OOCL"));
    }

    @Test
    void should_get_company_by_id_when_perform_given_company_id() throws Exception {
        //given
        Company company1 = jpaCompanyRepository.save(new Company(1, "OOCL", Collections.emptyList()));
        Company company2 = jpaCompanyRepository.save(new Company(22, "IQAX", Collections.emptyList()));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("OOCL"));
    }

    @Test
    void should_get_companies_by_page_when_perform_given_page_and_pageSize() throws Exception {
        //given
        jpaCompanyRepository.save(new Company(1, "CargoSmart", Collections.emptyList()));
        jpaCompanyRepository.save(new Company(2, "IQAX", Collections.emptyList()));
        Company company = jpaCompanyRepository.save(new Company(3, "OOIL", Collections.emptyList()));

        client.perform(MockMvcRequestBuilders.get("/companies")
                        .queryParam("page", "2").queryParam("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("OOIL"));

    }

    @Test
    void should_throw_not_found_company_exception_when_perform_given_wrong_company_id() throws Exception {
        //given

        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(exception -> assertThat("Not Found: company",
                        equalTo(Objects.requireNonNull(exception.getResolvedException()).getMessage())));
    }

    @Test
    void should_update_company__by_id_when_perform_given_update_company() throws Exception {
        //given
        Company company = jpaCompanyRepository.save(new Company(1, "OOCL", Collections.emptyList()));
        jpaEmployeeRepository.save(new Employee(1, "George", 18, "male", 333, company.getId()));
        CompanyRequest request = new CompanyRequest();
        request.setCompanyName("IQAX");
        request.setEmployees(Collections.singletonList(new Employee(2, "George111", 18, "male", 333, company.getId())));
        String requestAsString = new ObjectMapper().writeValueAsString(request);
        //when & then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("IQAX"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("George111"));
    }

    @Test
    void should_delete_company_by_id_when_perform_given_delete_company_id() throws Exception {
        //given
        Company company = jpaCompanyRepository.save(new Company(1, "OOCL", Collections.emptyList()));

        //when & then
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void should_get_all_employees_when_perform_given_company_id() throws Exception {
        //given
        Company company = jpaCompanyRepository.save(new Company(1, "OOCL", Collections.emptyList()));
        jpaEmployeeRepository.save(new Employee(1, "George1", 18, "male", 188, company.getId()));
        jpaEmployeeRepository.save(new Employee(2, "George2", 18, "female", 18888, company.getId()));

        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", company.getId())
                        .queryParam("gender", "female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("George1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("George2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(188))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(18888));
    }

}
