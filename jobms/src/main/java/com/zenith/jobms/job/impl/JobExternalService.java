package com.zenith.jobms.job.impl;

import com.zenith.jobms.job.clients.CompanyClient;
import com.zenith.jobms.job.clients.ReviewClient;
import com.zenith.jobms.job.external.Company;
import com.zenith.jobms.job.external.Review;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class JobExternalService {

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyFallback")
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyFallback")
    public Company getCompanyWithCircuitBreaker(Long companyId) {
        return companyClient.getCompanyById(companyId);
    }

    @CircuitBreaker(name = "reviewBreaker", fallbackMethod = "reviewFallback")
    @RateLimiter(name = "reviewBreaker", fallbackMethod = "reviewFallback")
    public List<Review> getReviewsWithCircuitBreaker(Long companyId) {
        return reviewClient.getReviewsByCompanyId(companyId);
    }

    // Fallback method for company service
    public Company companyFallback(Long companyId, Exception e) {
        System.out.println("Company service is down. Fallback triggered for companyId: " + companyId);
        Company fallbackCompany = new Company();
        fallbackCompany.setId(companyId);
        fallbackCompany.setName("Company Service Unavailable");
        fallbackCompany.setDescription("Service is temporarily down");
        return fallbackCompany;
    }

    // Fallback method for review service
    public List<Review> reviewFallback(Long companyId, Exception e) {
        System.out.println("Review service is down. Fallback triggered for companyId: " + companyId);
        return Collections.emptyList(); // Return empty list when review service is down
    }
}
