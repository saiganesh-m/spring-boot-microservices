package com.zenith.jobms.job;

import com.zenith.jobms.job.dto.JobDTO;

import java.util.*;

public interface JobService {
    List<JobDTO> findAllJobs();

    JobDTO findJobById(Long id);

    boolean createJob(Job job);

    boolean updateJobById(Long id, Job updatedJob);

    boolean deleteJobById(Long id);
}
