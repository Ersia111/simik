package com.simik.simik.service;

import com.simik.simik.dto.EmployeeProfileRequest;
import com.simik.simik.entity.EmployeeProfile;

public interface EmployeeProfileService {

    void createOrUpdateProfile(EmployeeProfileRequest request);

    EmployeeProfile getProfileByEmployeeEmail(String email);
}