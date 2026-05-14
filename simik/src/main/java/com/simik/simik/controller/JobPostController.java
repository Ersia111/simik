package com.simik.simik.controller;

import com.simik.simik.entity.JobPost;
import com.simik.simik.dto.JobPostRequest;
import com.simik.simik.service.JobPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createJobPost(@RequestBody JobPostRequest request) {
        try {
            jobPostService.createJobPost(request);
            return ResponseEntity.ok("Njoftimi u krijua me sukses.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<JobPost>> getAllJobs() {
        return ResponseEntity.ok(jobPostService.getAllActiveJobPosts());
    }
}