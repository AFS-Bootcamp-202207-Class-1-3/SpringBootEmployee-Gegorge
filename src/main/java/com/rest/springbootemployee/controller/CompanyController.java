package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.service.CompanyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyImpl companyImpl;
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyImpl.findAllCompanies();
    }

    @GetMapping(path = "/{id}")
    public Company getCompaniesById(@PathVariable Integer id) {
        return companyImpl.findCompanyById(id);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        return companyImpl.findAllEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompanyByPage(@RequestParam int page, int pageSize) {
        return companyImpl.findCompanyByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> addCompany(@RequestBody Company company) {
        return companyImpl.addCompany(company);
    }

    @PutMapping(path = "/{id}")
    public Company updateCompanyById(@PathVariable Integer id) {
        return companyImpl.updateCompanyById(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id) {
        companyImpl.removeCompanyById(id);
    }
}
