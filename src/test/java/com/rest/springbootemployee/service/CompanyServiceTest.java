package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
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
public class CompanyServiceTest {
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyServiceImpl companyService;
    @Test
    void should_return_all_employee_when_get_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90));
        List<Company> companies = Arrays.asList(
                new Company(1,"OOCL",employees),
                new Company(2,"IQAX",employees));
        given(companyRepository.findAllCompanies()).willReturn(companies);

        //when
        List<Company> actualCompanies = companyService.findAllCompanies();

        //then
        assertThat(actualCompanies.size(), equalTo(2));
        assertThat(actualCompanies.get(0).getCompanyName(), equalTo("OOCL"));
        assertThat(actualCompanies.get(1).getCompanyName(), equalTo("IQAX"));
    }
    @Test
    void should_return_company_when_find_by_id_given_company_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90));
        Company company = new Company(1, "OOCL", employees);
        given(companyRepository.findCompanyById(1)).willReturn(company);

        //when
        Company actualCompany = companyService.findCompanyById(1);

        //then
        assertThat(actualCompany.getCompanyName(), equalTo("OOCL"));
    }

    @Test
    void should_return_updated_Company_when_update_given_employee() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90));
        Company company = new Company(1, "OOCL", employees);
        Company newCompany = new Company(1, "IQAX", employees);
        given(companyRepository.findCompanyById(1)).willReturn(company);
        given(companyRepository.updateCompanyById(1, newCompany)).willCallRealMethod();

        //when
        Company updateCompany = companyService.updateCompanyById(1, newCompany);

        //then
        verify(companyRepository).updateCompanyById(1, newCompany);
    }

}
