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
public class CompanyImpl implements ICompany{
    @Autowired
    private CompanyRepository companyRepository;
    public List<Company> findAllCompanies() {
        return companyRepository.companies;
    }

    public Company findCompanyById(Integer id) {
        return companyRepository.companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchCompanyException::new);
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return findCompanyById(id).getEmployees();
    }

    public List<Company> findCompanyByPage(int page, int pageSize) {
        return companyRepository.companies.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Company> addCompany(Company company) {
        companyRepository.companies.add(new Company(generateCompanyId(),
                company.getCompanyName(),company.getEmployees()));
        return companyRepository.companies;
    }

    public Integer generateCompanyId() {
        return companyRepository.companies.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0) + 1;
    }

    public Company updateCompanyById(Integer id) {
        Company updateCompany = findCompanyById(id);
        updateCompany.updateCompanyName();
        return updateCompany;
    }

    public void removeCompanyById(Integer id) {
        companyRepository.companies.remove(findCompanyById(id));
    }
}
