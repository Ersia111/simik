package com.simik.simik.service.impl;

import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.entity.User;
import com.simik.simik.repository.EmployeeProfileRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public FileUploadServiceImpl(UserRepository userRepository,
                                 EmployeeProfileRepository employeeProfileRepository) {
        this.userRepository = userRepository;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    @Override
    public String uploadCv(String employeeEmail, MultipartFile file) {
        return saveFile(employeeEmail, file, "cv", true);
    }

    @Override
    public String uploadPortfolio(String employeeEmail, MultipartFile file) {
        return saveFile(employeeEmail, file, "portfolio", false);
    }

    private String saveFile(String employeeEmail, MultipartFile file, String folderName, boolean isCv) {
        try {
            User employee = userRepository.findByEmail(employeeEmail)
                    .orElseThrow(() -> new RuntimeException("Punonjesi nuk u gjet."));

            if (!employee.getRole().getName().equals("PUNONJES")) {
                throw new RuntimeException("Vetem punonjesi mund te ngarkoje dokumente.");
            }

            EmployeeProfile profile = employeeProfileRepository.findByEmployee(employee)
                    .orElseThrow(() -> new RuntimeException("Profili i punonjesit nuk u gjet."));

            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.isBlank()) {
                throw new RuntimeException("Emri i file-it nuk eshte i vlefshem.");
            }

            String cleanedFilename = originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");

            String targetDirPath = uploadDir + File.separator + folderName;
            File targetDir = new File(targetDirPath);

            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            String safeFilename = employeeEmail.replace("@", "_").replace(".", "_") + "_" + cleanedFilename;
            String fullPath = targetDirPath + File.separator + safeFilename;

            file.transferTo(new File(fullPath));

            String savedPath = folderName + "/" + safeFilename;

            if (isCv) {
                profile.setCvPath(savedPath);
            } else {
                profile.setPortfolioPath(savedPath);
            }

            employeeProfileRepository.save(profile);

            return savedPath;

        } catch (Exception e) {
            throw new RuntimeException("Gabim gjate ruajtjes se file-it: " + e.getMessage());
        }
    }}