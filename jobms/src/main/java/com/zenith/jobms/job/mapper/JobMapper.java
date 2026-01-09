package com.zenith.jobms.job.mapper;

import java.util.List;

import com.zenith.jobms.job.dto.JobDTO;
import com.zenith.jobms.job.external.Company;
import com.zenith.jobms.job.external.Review;
import com.zenith.jobms.job.Job;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDTO(Job job, Company company, List<Review> reviews) {
        JobDTO jobWithCompanyDTO = new JobDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReview(reviews);
        return jobWithCompanyDTO;
    }
}
