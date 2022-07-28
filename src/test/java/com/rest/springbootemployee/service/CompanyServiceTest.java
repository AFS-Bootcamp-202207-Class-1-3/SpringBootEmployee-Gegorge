package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
//import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
public class CompanyServiceTest {
    @Mock
    JpaCompanyRepository jpaCompanyRepository;
    @InjectMocks
    CompanyServiceImpl companyService;
    @Test
    void should_return_all_employee_when_get_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90, 100));
        List<Company> companies = Arrays.asList(
                new Company(1,"OOCL",employees),
                new Company(2,"IQAX",employees));
        given(jpaCompanyRepository.findAll()).willReturn(companies);

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
        employees.add(new Employee(1, "George", 23, "male", 90, 100));
        Company company = new Company(1, "OOCL", employees);
        given(jpaCompanyRepository.findById(1)).willReturn(Optional.of(company));

        //when
        Company actualCompany = companyService.findCompanyById(1);

        //then
        assertThat(actualCompany.getCompanyName(), equalTo("OOCL"));
    }

    @Test
    void should_return_updated_Company_when_update_given_employee() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George", 23, "male", 90, 100));
        Company company = new Company(1, "OOCL", employees);
        Company newCompany = new Company(1, "IQAX", employees);
        given(jpaCompanyRepository.findById(1)).willReturn(Optional.of(company));
        given(jpaCompanyRepository.saveAndFlush(newCompany)).willCallRealMethod();

        //when
        Company updateCompany = companyService.updateCompanyById(1, newCompany);

        //then
        verify(jpaCompanyRepository).saveAndFlush(company);
    }
    @Test
    void should_return_employees_when_find_employees_given_company_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George1", 23, "male", 902, 100));
        employees.add(new Employee(2, "George2", 23, "male", 903, 100));
        Company company = new Company(1, "OOCL", employees);

        given(jpaCompanyRepository.findById(1)).willReturn(Optional.of(company));

        //when
        List<Employee> employeesByCompanyId = companyService.findAllEmployeesByCompanyId(1);

        //then
        assertThat(employeesByCompanyId.size(), equalTo(2));
    }

    @Test
    void should_return_id_when_add_company_given_company() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(3, "George", 23, "male", 90, 100));
        Company company = new Company(3, "OOCL", employees);
        given(jpaCompanyRepository.save(company)).willReturn(company);

        //when
        Company company1 = companyService.addCompany(company);

        //then
        assertThat(company1.getId(), equalTo(1));
    }

    @Test
    void should_remove_company_when_delete_company_given_company_id() {
        //given

        //when
        companyService.removeCompanyById(1);
        //then
        verify(jpaCompanyRepository,times(1)).deleteById(1);
    }

    @Test
    void should_return_companies_by_page_when_get_employee_given_page_and_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George1", 23, "male", 902, 100));
        employees.add(new Employee(2, "George2", 23, "male", 903, 100));
        List<Company> companies = Arrays.asList(
                new Company(1, "OOCL", employees),
                new Company(2, "IQAX",employees),
                new Company(3, "OOIL",employees)
        );
        PageRequest pageRequest = PageRequest.of(2,2);
        Page<Company> companyPage = new PageImpl<>(companies, pageRequest, companies.size());
        given(jpaCompanyRepository.findAll(pageRequest)).willReturn(companyPage);
        //when
        companyService.findCompanyByPage(3,2);
        //then
        verify(jpaCompanyRepository,times(1)).findAll(pageRequest);
    }

}
