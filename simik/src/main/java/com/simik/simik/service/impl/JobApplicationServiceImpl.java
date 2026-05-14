package com.simik.simik.service.impl;

import com.simik.simik.dto.JobApplicationRequest;
import com.simik.simik.entity.JobApplication;
import com.simik.simik.entity.JobPost;
import com.simik.simik.entity.User;
import com.simik.simik.repository.JobApplicationRepository;
import com.simik.simik.repository.JobPostRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.JobApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                     UserRepository userRepository,
                                     JobPostRepository jobPostRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public void applyToJob(JobApplicationRequest request) {

        User employee = userRepository.findByEmail(request.getEmployeeEmail())
                .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet."));

        if (!employee.getRole().getName().equals("PUNONJES")) {
            throw new RuntimeException("Vetem punonjesi mund te aplikoje.");
        }

        JobPost jobPost = jobPostRepository.findById(request.getJobPostId())
                .orElseThrow(() -> new RuntimeException("Njoftimi nuk u gjet."));

        if (!jobPost.isActive()) {
            throw new RuntimeException("Ky njoftim nuk eshte aktiv.");
        }

        boolean alreadyApplied = jobApplicationRepository
                .findByEmployeeAndJobPost(employee, jobPost)
                .isPresent();

        if (alreadyApplied) {
            throw new RuntimeException("Ju keni aplikuar tashme per kete njoftim.");
        }

        JobApplication application = new JobApplication();
        application.setEmployee(employee);
        application.setJobPost(jobPost);
        application.setAppliedAt(LocalDateTime.now());
        application.setStatus("APLIKUAR");

        jobApplicationRepository.save(application);
    }

    @Override
    public List<JobApplication> getApplicationsByJobPost(Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new RuntimeException("Njoftimi nuk u gjet."));

        return jobApplicationRepository.findByJobPost(jobPost);
    }

    @Override
    public List<JobApplication> getApplicationsByEmployeeEmail(String email) {
        User employee = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet."));

        return jobApplicationRepository.findByEmployee(employee);
    }
}