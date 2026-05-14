package com.simik.simik.repository;

import com.simik.simik.entity.JobPost;
import com.simik.simik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    List<JobPost> findByActiveTrue();

    List<JobPost> findByEmployer(User employer);

    List<JobPost> findByEmployerAndActiveTrue(User employer);

    List<JobPost> findTop6ByActiveTrueOrderByCreatedAtDesc();
}