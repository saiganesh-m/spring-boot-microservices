package com.zenith.jobms.job;

import java.util.*;

import com.zenith.jobms.job.dto.JobDTO;
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
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAllJobs() {
        return new ResponseEntity<>(jobService.findAllJobs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> findJobById(@PathVariable Long id) {
        JobDTO job = jobService.findJobById(id);
        if (job != null) {
            return new ResponseEntity<>(job, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        boolean isJobCreated = jobService.createJob(job);
        if (isJobCreated) {
            return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create job", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job updatedJob) {
        boolean isJobUpdated = jobService.updateJobById(id, updatedJob);
        if (isJobUpdated) {
            return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
        boolean isJobDeleted = jobService.deleteJobById(id);
        if (isJobDeleted) {
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        }
    }
}
