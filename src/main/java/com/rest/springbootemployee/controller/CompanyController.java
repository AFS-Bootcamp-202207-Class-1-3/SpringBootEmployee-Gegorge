package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAllCompanies();
    }

    @GetMapping(path = "/{id}")
    public Company getCompaniesById(@PathVariable Integer id) {
        return companyRepository.findCompanyById(id);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        return companyRepository.findAllEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompanyByPage(@RequestParam int page, int pageSize) {
        return companyRepository.findCompanyByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> addCompany(@RequestBody Company company) {
        return companyRepository.addCompany(company);
    }

    @PutMapping(path = "/{id}")
    public Company updateCompanyById(@PathVariable Integer id) {
        return companyRepository.updateCompanyById(id);
    }
}
