package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyServiceImpl.findAllCompanies();
    }

    @GetMapping(path = "/{id}")
    public Company getCompaniesById(@PathVariable Integer id) {
        return companyServiceImpl.findCompanyById(id);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        return companyServiceImpl.findAllEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompanyByPage(@RequestParam int page, int pageSize) {
        return companyServiceImpl.findCompanyByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addCompany(@RequestBody Company company) {
        return companyServiceImpl.addCompany(company);
    }

    @PutMapping(path = "/{id}")
    public Company updateCompanyById(@PathVariable Integer id) {
        return companyServiceImpl.updateCompanyById(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id) {
        companyServiceImpl.removeCompanyById(id);
    }
}
