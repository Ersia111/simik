package com.simik.simik.service.impl;

import com.simik.simik.entity.EmployeeProfile;
import com.simik.simik.entity.User;
import com.simik.simik.repository.EmployeeProfileRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public FileUploadServiceImpl(
            UserRepository userRepository,
            EmployeeProfileRepository employeeProfileRepository
    ) {
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
                    .orElseGet(() -> {
                        EmployeeProfile newProfile = new EmployeeProfile();
                        newProfile.setEmployee(employee);
                        return employeeProfileRepository.save(newProfile);
                    });

            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.isBlank()) {
                throw new RuntimeException("Emri i file-it nuk eshte i vlefshem.");
            }

            String cleanedFilename = originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
            String safeEmail = employeeEmail.replace("@", "_").replace(".", "_");
            String safeFilename = safeEmail + "_" + cleanedFilename;

            Path targetDir = Paths.get(uploadDir, folderName).toAbsolutePath().normalize();
            Files.createDirectories(targetDir);

            Path targetFile = targetDir.resolve(safeFilename).normalize();

            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

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
    }
}