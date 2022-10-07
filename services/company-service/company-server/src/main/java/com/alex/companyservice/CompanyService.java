package com.alex.companyservice;

import com.alex.companyservice.clients.CompanyDto;
import com.alex.companyservice.exception.CompanyErrorCode;
import com.alex.companyservice.utility.MapperUtility;
import com.alex.exceptionhandler.api.GlobalApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public record CompanyService(CompanyRepository companyRepository) {

    public CompanyDto getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new GlobalApiRequestException(
                        CompanyErrorCode.ERROR_ENTITY_NOT_FOUND,
                        HttpStatus.BAD_REQUEST
                ));

        return MapperUtility.map(company, CompanyDto.class);
    }

    public CompanyDto saveCompany(CompanyDto companyDto) {
        Company company = MapperUtility.map(companyDto, Company.class);
        company = companyRepository.save(company);
        return MapperUtility.map(company, CompanyDto.class);
    }
}
