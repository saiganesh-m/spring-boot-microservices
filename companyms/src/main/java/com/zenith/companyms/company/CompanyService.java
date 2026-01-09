package com.zenith.companyms.company;

import java.util.List;

import com.zenith.companyms.company.dto.ReviewMessage;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company getCompanyById(Long id);

    boolean createCompany(Company company);

    boolean updateCompanyById(Long id, Company company);

    boolean deleteCompany(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
