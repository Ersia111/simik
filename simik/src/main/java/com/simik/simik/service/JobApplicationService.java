package com.simik.simik.service;

import com.simik.simik.dto.JobApplicationRequest;
import com.simik.simik.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    void applyToJob(JobApplicationRequest request);

    List<JobApplication> getApplicationsByJobPost(Long jobPostId);

    List<JobApplication> getApplicationsByEmployeeEmail(String email);
}