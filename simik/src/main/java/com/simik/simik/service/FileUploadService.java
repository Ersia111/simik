package com.simik.simik.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadCv(String employeeEmail, MultipartFile file);
    String uploadPortfolio(String employeeEmail, MultipartFile file);
}