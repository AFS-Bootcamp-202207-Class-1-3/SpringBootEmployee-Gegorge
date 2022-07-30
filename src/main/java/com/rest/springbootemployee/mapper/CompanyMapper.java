package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.vo.CompanyResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<CompanyResponse> convertToVOs(List<Company> companies) {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companies) {
            CompanyResponse response = new CompanyResponse();
            BeanUtils.copyProperties(company, response);
            responses.add(response);
        }
        return responses;
    }

}
