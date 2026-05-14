package com.simik.simik.repository;

import com.simik.simik.entity.JobApplication;
import com.simik.simik.entity.JobPost;
import com.simik.simik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobPost(JobPost jobPost);

    List<JobApplication> findByEmployee(User employee);

    Optional<JobApplication> findByEmployeeAndJobPost(User employee, JobPost jobPost);
}