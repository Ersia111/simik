package com.simik.simik.dto;

import jakarta.validation.constraints.NotBlank;

public class JobPostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String criteria;

    @NotBlank
    private String benefits;

    @NotBlank
    private String employerEmail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }
}