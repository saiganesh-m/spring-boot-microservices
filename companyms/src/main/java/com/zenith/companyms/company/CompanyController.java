package com.zenith.companyms.company;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        boolean isCreated = companyService.createCompany(company);
        if (isCreated) {
            return new ResponseEntity<>("Company created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create company", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long id, @RequestBody Company company) {
        boolean isUpdated = companyService.updateCompanyById(id, company);
        if (isUpdated) {
            return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update company", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        boolean isDeleted = companyService.deleteCompany(id);
        if (isDeleted) {
            return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete company", HttpStatus.BAD_REQUEST);
        }
    }

}
