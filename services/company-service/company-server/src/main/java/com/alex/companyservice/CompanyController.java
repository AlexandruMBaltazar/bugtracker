package com.alex.companyservice;

import com.alex.companyservice.clients.CompanyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/companies")
public record CompanyController(CompanyService companyService) {

    @PostMapping
    public ResponseEntity<CompanyDto> saveCompany(@Valid @RequestBody CompanyDto companyDto) {
        companyDto = companyService.saveCompany(companyDto);
        return new ResponseEntity<>(companyDto, HttpStatus.CREATED);
    }
}
