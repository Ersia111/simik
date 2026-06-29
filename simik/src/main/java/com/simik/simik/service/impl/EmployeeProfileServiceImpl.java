package com.simik.simik.service.impl;

import com.simik.simik.dto.EmployeeProfileRequest;
import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.entity.User;
import com.simik.simik.repository.EmployeeProfileRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final UserRepository userRepository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository employeeProfileRepository,
                                      UserRepository userRepository) {
        this.employeeProfileRepository = employeeProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrUpdateProfile(EmployeeProfileRequest request) {
        User employee = userRepository.findByEmail(request.getEmployeeEmail())
                .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet."));

        if (!employee.getRole().getName().equals("PUNONJES")) {
            throw new RuntimeException("Vetem punonjesi mund te krijoje profil.");
        }

        EmployeeProfile profile = employeeProfileRepository.findByEmployee(employee)
                .orElse(new EmployeeProfile());

        profile.setEmployee(employee);
        profile.setFullName(request.getFullName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setProfession(request.getProfession());
        profile.setSkills(request.getSkills());
        profile.setBio(request.getBio());
        profile.setCvPath(request.getCvPath());
        profile.setPortfolioPath(request.getPortfolioPath());

        employeeProfileRepository.save(profile);
    }

    @Override
    public EmployeeProfile getProfileByEmployeeEmail(String email) {
        User employee = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet."));

        return employeeProfileRepository.findByEmployee(employee)
                .orElseThrow(() -> new RuntimeException("Profili nuk u gjet."));
    }
}