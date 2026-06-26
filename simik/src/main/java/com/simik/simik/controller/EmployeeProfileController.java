package com.simik.simik.controller;

import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.service.EmployeeProfileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employee-profile")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @PostMapping(value = "/save", consumes = "multipart/form-data")
    public ResponseEntity<String> saveProfile(
            @RequestParam String employeeEmail,
            @RequestParam String fullName,
            @RequestParam String phoneNumber,
            @RequestParam String profession,
            @RequestParam String skills,
            @RequestParam String bio,
            @RequestParam(required = false) MultipartFile cv,
            @RequestParam(required = false) MultipartFile portfolio
    ) {
        try {
            employeeProfileService.createOrUpdateProfile(
                    employeeEmail,
                    fullName,
                    phoneNumber,
                    profession,
                    skills,
                    bio,
                    cv,
                    portfolio
            );

            return ResponseEntity.ok("Profili u ruajt me sukses.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmployeeProfile> getProfile(@PathVariable String email) {
        return ResponseEntity.ok(employeeProfileService.getProfileByEmployeeEmail(email));
    }

    @GetMapping("/{email}/cv")
    public ResponseEntity<byte[]> downloadCv(@PathVariable String email) {
        EmployeeProfile profile = employeeProfileService.getProfileByEmployeeEmail(email);

        if (profile.getCvFile() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        profile.getCvContentType() != null ? profile.getCvContentType() : "application/octet-stream"
                ))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + profile.getCvFileName() + "\"")
                .body(profile.getCvFile());
    }

    @GetMapping("/{email}/portfolio")
    public ResponseEntity<byte[]> downloadPortfolio(@PathVariable String email) {
        EmployeeProfile profile = employeeProfileService.getProfileByEmployeeEmail(email);

        if (profile.getPortfolioFile() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        profile.getPortfolioContentType() != null ? profile.getPortfolioContentType() : "application/octet-stream"
                ))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + profile.getPortfolioFileName() + "\"")
                .body(profile.getPortfolioFile());
    }
}