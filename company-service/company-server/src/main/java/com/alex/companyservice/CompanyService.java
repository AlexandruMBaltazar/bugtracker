package com.alex.companyservice;

import com.alex.companyservice.clients.CompanyDto;
import com.alex.companyservice.utility.MapperUtility;
import org.springframework.stereotype.Service;

@Service
public record CompanyService(CompanyRepository companyRepository) {

    public CompanyDto saveCompany(CompanyDto companyDto) {
        Company company = MapperUtility.map(companyDto, Company.class);
        company = companyRepository.save(company);
        return MapperUtility.map(company, CompanyDto.class);
    }
}
