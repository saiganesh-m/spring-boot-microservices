package com.zenith.jobms.job.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zenith.jobms.job.external.Company;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    Company getCompanyById(@PathVariable("id") Long id);
}
