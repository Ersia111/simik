package com.simik.simik.service.impl;

import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.entity.User;
import com.simik.simik.repository.EmployeeProfileRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.EmployeeProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public void createOrUpdateProfile(String employeeEmail,
                                      String fullName,
                                      String phoneNumber,
                                      String profession,
                                      String skills,
                                      String bio,
                                      MultipartFile cv,
                                      MultipartFile portfolio) throws IOException {

        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet."));

        if (!employee.getRole().getName().equals("PUNONJES")) {
            throw new RuntimeException("Vetem punonjesi mund te krijoje profil.");
        }

        EmployeeProfile profile = employeeProfileRepository.findByEmployee(employee)
                .orElse(new EmployeeProfile());

        profile.setEmployee(employee);
        profile.setFullName(fullName);
        profile.setPhoneNumber(phoneNumber);
        profile.setProfession(profession);
        profile.setSkills(skills);
        profile.setBio(bio);

        if (cv != null && !cv.isEmpty()) {
            profile.setCvFile(cv.getBytes());
            profile.setCvFileName(cv.getOriginalFilename());
            profile.setCvContentType(cv.getContentType());
            profile.setCvPath("/api/employee-profile/" + employeeEmail + "/cv");
        }

        if (portfolio != null && !portfolio.isEmpty()) {
            profile.setPortfolioFile(portfolio.getBytes());
            profile.setPortfolioFileName(portfolio.getOriginalFilename());
            profile.setPortfolioContentType(portfolio.getContentType());
            profile.setPortfolioPath("/api/employee-profile/" + employeeEmail + "/portfolio");
        }

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