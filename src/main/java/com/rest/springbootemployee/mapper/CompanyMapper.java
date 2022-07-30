package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.vo.CompanyResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company convertToEntity(CompanyRequest request) {
        Company company = new Company();
        BeanUtils.copyProperties(request, company);
        return company;
    }

    public CompanyResponse convertToVO(Company company) {
        CompanyResponse response = new CompanyResponse();
        BeanUtils.copyProperties(company, response);
        return response;
    }

}
