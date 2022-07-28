package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NoSuchCompanyException;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl {
    @Autowired
    private CompanyRepository companyRepository;
    public List<Company> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }

    public Company findCompanyById(Integer id) {
        return companyRepository.findCompanyById(id);
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return companyRepository.findAllEmployeeByCompanyId(id);
    }

    public List<Company> findCompanyByPage(int page, int pageSize) {
        return companyRepository.findCompanyByPage(page, pageSize);
    }

    public int addCompany(Company company) {
        return companyRepository.addCompany(company);
    }


    public Company updateCompanyById(Integer id,Company updateCompany) {
        return companyRepository.updateCompanyById(id, updateCompany);
    }

    public void removeCompanyById(Integer id) {
        companyRepository.removeById(id);
    }

    public void clearAll() {
        companyRepository.companies.clear();
    }
}
