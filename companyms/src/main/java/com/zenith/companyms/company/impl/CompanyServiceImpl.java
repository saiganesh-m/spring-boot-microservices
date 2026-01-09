package com.zenith.companyms.company.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenith.companyms.company.Company;
import com.zenith.companyms.company.CompanyRepository;
import com.zenith.companyms.company.CompanyService;
import com.zenith.companyms.company.clients.ReviewClient;
import com.zenith.companyms.company.dto.ReviewMessage;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReviewClient reviewClient;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createCompany(Company company) {
        companyRepository.save(company);
        return true;
    }

    @Override
    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCompanyById(Long id, Company company) {
        Optional<Company> existingCompanyOpt = companyRepository.findById(id);
        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();
            existingCompany.setName(company.getName());
            existingCompany.setDescription(company.getDescription());
            companyRepository.save(existingCompany);
            return true;
        }
        return false;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElse(null);
        double averageRating = reviewClient.getAverageRating(reviewMessage.getCompanyId());
        if (company != null) {
            company.setAverageRating(averageRating);
            companyRepository.save(company);
        }

    }

}
