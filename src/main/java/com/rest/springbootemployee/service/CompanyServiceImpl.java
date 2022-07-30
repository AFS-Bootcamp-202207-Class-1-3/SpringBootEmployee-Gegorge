package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl {
    @Autowired
    private JpaCompanyRepository jpaCompanyRepository;

    public List<Company> findAllCompanies() {
        return jpaCompanyRepository.findAll();
    }

    public Company findCompanyById(Integer id) {
        return jpaCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Company.class.getSimpleName()));
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return findCompanyById(id).getEmployees();
    }

    public List<Company> findCompanyByPage(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return jpaCompanyRepository.findAll(pageRequest).toList();
    }

    public Company addCompany(Company company) {
        return jpaCompanyRepository.save(company);
    }


    public Company updateCompanyById(Integer id, Company updateCompany) {
        Company company = findCompanyById(id);
        company.merge(updateCompany);
        return jpaCompanyRepository.saveAndFlush(company);
    }

    public void removeCompanyById(Integer id) {
        jpaCompanyRepository.deleteById(id);
    }
}
