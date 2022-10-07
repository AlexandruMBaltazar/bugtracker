package com.alex.companyservice;

import com.alex.companyservice.clients.CompanyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/companies")
public record CompanyController(CompanyService companyService) {

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long companyId) {
        CompanyDto companyDto = companyService.getCompany(companyId);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> saveCompany(@Valid @RequestBody CompanyDto companyDto) {
        companyDto = companyService.saveCompany(companyDto);
        return new ResponseEntity<>(companyDto, HttpStatus.CREATED);
    }
}
