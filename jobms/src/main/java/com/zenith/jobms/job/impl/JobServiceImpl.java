package com.zenith.jobms.job.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.zenith.jobms.job.dto.JobDTO;
import com.zenith.jobms.job.external.Company;
import com.zenith.jobms.job.external.Review;
import com.zenith.jobms.job.mapper.JobMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zenith.jobms.job.Job;
import com.zenith.jobms.job.JobRepository;
import com.zenith.jobms.job.JobService;
import com.zenith.jobms.job.clients.CompanyClient;
import com.zenith.jobms.job.clients.ReviewClient;

import org.springframework.web.client.RestTemplate;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private JobExternalService jobExternalService;

    // @Autowired
    // private CompanyClient companyClient;
    //
    // @Autowired
    // private ReviewClient reviewClient;

    @Override
    @Retry(name = "companyBreaker", fallbackMethod = "findAllJobsFallback")
    public List<JobDTO> findAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job) {
        // Use external service to ensure AOP proxy works
        Company company = jobExternalService.getCompanyWithCircuitBreaker(job.getCompanyId());
        List<Review> reviews = jobExternalService.getReviewsWithCircuitBreaker(job.getCompanyId());

        JobDTO jobWithCompanyDTO = JobMapper.mapToJobWithCompanyDTO(job, company, reviews);
        return jobWithCompanyDTO;
    }

    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findJobByIdFallback")
    @Override
    public JobDTO findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }
        return convertToDto(job);
    }

    // Fallback for findJobById
    private JobDTO findJobByIdFallback(Long id, Exception e) {
        System.out.println("findJobById fallback triggered for jobId: " + id);
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }
        // Return job without company and reviews
        return JobMapper.mapToJobWithCompanyDTO(job, null, Collections.emptyList());
    }

    // Fallback for findAllJobs
    public List<JobDTO> findAllJobsFallback(Exception e) {
        System.out.println("findAllJobs fallback triggered due to: " + e.getMessage());
        List<Job> jobs = jobRepository.findAll();
        // Return jobs without company/reviews, or empty list, depending on business
        // requirement
        // Here returning jobs with null company/reviews as graceful degradation
        return jobs.stream()
                .map(job -> JobMapper.mapToJobWithCompanyDTO(job, null, Collections.emptyList()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean createJob(Job job) {
        jobRepository.save(job);
        return true;
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> existingJobOpt = jobRepository.findById(id);
        if (existingJobOpt.isPresent()) {
            Job existingJob = existingJobOpt.get();
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setMinSalary(updatedJob.getMinSalary());
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
            existingJob.setLocation(updatedJob.getLocation());
            jobRepository.save(existingJob);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }
}