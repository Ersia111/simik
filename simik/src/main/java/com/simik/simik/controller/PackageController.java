package com.simik.simik.controller;

import com.simik.simik.dto.BuyPackageRequest;
import com.simik.simik.entity.EmployerSubscription;
import com.simik.simik.entity.SubscriptionPackage;
import com.simik.simik.service.PackageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "*")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionPackage>> getAllPackages() {
        return ResponseEntity.ok(packageService.getAllPackages());
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyPackage(
            @RequestBody BuyPackageRequest request
    ) {
        try {
            packageService.buyPackage(request);

            return ResponseEntity.ok(
                    "Kërkesa për paketë u dërgua për aprovim nga admini."
            );

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EmployerSubscription>> getPendingSubscriptions() {
        return ResponseEntity.ok(
                packageService.getPendingSubscriptions()
        );
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveSubscription(
            @PathVariable Long id
    ) {
        try {
            packageService.approveSubscription(id);

            return ResponseEntity.ok(
                    "Paketa u aprovua me sukses."
            );

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectSubscription(
            @PathVariable Long id
    ) {
        try {
            packageService.rejectSubscription(id);

            return ResponseEntity.ok(
                    "Paketa u refuzua."
            );

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("/employer/{email}")
    public ResponseEntity<List<EmployerSubscription>> getSubscriptionsByEmployer(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(
                packageService.getSubscriptionsByEmployerEmail(email)
        );
    }
}