package com.simik.simik.controller;

import com.simik.simik.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload-cv")
    public ResponseEntity<String> uploadCv(@RequestParam("email") String email,
                                           @RequestParam("file") MultipartFile file) {
        try {
            String path = fileUploadService.uploadCv(email, file);
            return ResponseEntity.ok("CV u ngarkua me sukses: " + path);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload-portfolio")
    public ResponseEntity<String> uploadPortfolio(@RequestParam("email") String email,
                                                  @RequestParam("file") MultipartFile file) {
        try {
            String path = fileUploadService.uploadPortfolio(email, file);
            return ResponseEntity.ok("Portfolio u ngarkua me sukses: " + path);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}