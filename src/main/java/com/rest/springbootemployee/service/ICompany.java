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
    List<Company> addCompany(Company company);
    Integer generateCompanyId();
    Company updateCompanyById(Integer id);
    void removeCompanyById(Integer id);
}
