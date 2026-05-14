package com.simik.simik.service;

import com.simik.simik.dto.JobPostRequest;
import com.simik.simik.entity.JobPost;

import java.util.List;

public interface JobPostService {
    void createJobPost(JobPostRequest request);
    List<JobPost> getAllActiveJobPosts();
}