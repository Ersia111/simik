package com.simik.simik.service;

import com.simik.simik.entity.EmployeeProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeProfileService {

    void createOrUpdateProfile(String employeeEmail,
                               String fullName,
                               String phoneNumber,
                               String profession,
                               String skills,
                               String bio,
                               MultipartFile cv,
                               MultipartFile portfolio) throws IOException;

    EmployeeProfile getProfileByEmployeeEmail(String email);
}