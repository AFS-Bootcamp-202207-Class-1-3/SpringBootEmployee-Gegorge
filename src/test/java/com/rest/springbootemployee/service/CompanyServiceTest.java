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
import static org.mockito.Mockito.times;
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
    @Test
    void should_return_employees_when_find_employees_by_gender_given_company_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "George1", 23, "male", 902));
        employees.add(new Employee(2, "George2", 23, "male", 903));
        given(companyRepository.findAllEmployeeByCompanyId(1)).willReturn(employees);

        //when
        List<Employee> employeesByCompanyId = companyService.findAllEmployeesByCompanyId(1);

        //then
        assertThat(employeesByCompanyId.size(), equalTo(2));
    }

    @Test
    void should_return_id_when_add_company_given_company() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee( 3, "George", 23, "male", 90));
        Company company = new Company(3, "OOCL", employees);
        given(companyRepository.addCompany(company)).willReturn(1);

        //when
        Integer id = companyService.addCompany(company);

        //then
        assertThat(id, equalTo(1));
    }

    @Test
    void should_remove_company_when_delete_company_given_company_id() {
        //given

        //when
        companyService.removeCompanyById(1);
        //then
        verify(companyRepository,times(1)).removeById(1);
    }

}
