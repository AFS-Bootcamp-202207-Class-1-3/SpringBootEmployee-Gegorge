package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.mapper.CompanyMapper;
import com.rest.springbootemployee.mapper.EmployeeMapper;
import com.rest.springbootemployee.service.CompanyServiceImpl;
import com.rest.springbootemployee.vo.CompanyResponse;
import com.rest.springbootemployee.vo.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        List<Company> companies = companyServiceImpl.findAllCompanies();
        return companyMapper.convertToVOs(companies);
    }

    @GetMapping(path = "/{id}")
    public CompanyResponse getCompaniesById(@PathVariable Integer id) {
        Company company = companyServiceImpl.findCompanyById(id);
        return companyMapper.convertToVO(company);
    }

    @GetMapping(path = "/{id}/employees")
    public List<EmployeeResponse> getAllEmployeesByCompanyId(@PathVariable Integer id) {
        List<Employee> employees = companyServiceImpl.findAllEmployeesByCompanyId(id);
        return employeeMapper.convertToVOs(employees);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompanyByPage(@RequestParam int page, int pageSize) {
        List<Company> companies = companyServiceImpl.findCompanyByPage(page, pageSize);
        return companyMapper.convertToVOs(companies);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody CompanyRequest request) {
        Company company = companyServiceImpl.addCompany(companyMapper.convertToEntity(request));
        return companyMapper.convertToVO(company);
    }

    @PutMapping(path = "/{id}")
    public CompanyResponse updateCompanyById(@PathVariable Integer id, @RequestBody CompanyRequest request) {
        Company company = companyServiceImpl.updateCompanyById(id, companyMapper.convertToEntity(request));
        return companyMapper.convertToVO(company);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id) {
        companyServiceImpl.removeCompanyById(id);
    }
}
