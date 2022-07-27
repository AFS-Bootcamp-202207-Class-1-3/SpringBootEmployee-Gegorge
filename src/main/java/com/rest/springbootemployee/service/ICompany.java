package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICompany {
    Company findCompanyById(Integer id);
    List<Employee> findAllEmployeesByCompanyId(Integer id);
    List<Company> findCompanyByPage(int page, int pageSize);
    int addCompany(Company company);
    Company updateCompanyById(Integer id, Company company);
    void removeCompanyById(Integer id);
}
