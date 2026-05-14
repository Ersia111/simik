package com.simik.simik.dto;

import jakarta.validation.constraints.NotBlank;

public class BuyPackageRequest {

    @NotBlank
    private String employerEmail;

    @NotBlank
    private String packageName;

    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}