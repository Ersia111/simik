package com.simik.simik.controller;

import com.simik.simik.dto.EmployeeProfileRequest;
import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.service.EmployeeProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee-profile")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveProfile(@RequestBody EmployeeProfileRequest request) {
        try {
            employeeProfileService.createOrUpdateProfile(request);
            return ResponseEntity.ok("Profili u ruajt me sukses.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmployeeProfile> getProfile(@PathVariable String email) {
        return ResponseEntity.ok(employeeProfileService.getProfileByEmployeeEmail(email));
    }
}