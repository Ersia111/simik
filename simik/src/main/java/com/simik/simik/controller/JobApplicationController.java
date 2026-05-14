package com.simik.simik.controller;

import com.simik.simik.dto.JobApplicationRequest;
import com.simik.simik.entity.JobApplication;
import com.simik.simik.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyToJob(@RequestBody JobApplicationRequest request) {
        try {
            jobApplicationService.applyToJob(request);
            return ResponseEntity.ok("Aplikimi u krye me sukses.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job/{jobPostId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(@PathVariable Long jobPostId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJobPost(jobPostId));
    }
    @GetMapping("/employee/{email}")
    public ResponseEntity<List<JobApplication>> getApplicationsByEmployee(@PathVariable String email) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByEmployeeEmail(email));
    }
}