package com.simik.simik.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_profiles")
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phoneNumber;
    private String profession;
    private String skills;
    private String location;
    private String linkedinUrl;
    private String githubUrl;
    private String profileImage;

    @Column(length = 2000)
    private String bio;

    private String cvPath;
    private String portfolioPath;

    private String cvFileName;
    private String cvContentType;

    @JsonIgnore
    @Column(name = "cv_file", columnDefinition = "bytea")
    private byte[] cvFile;

    private String portfolioFileName;
    private String portfolioContentType;

    @JsonIgnore
    @Column(name = "portfolio_file", columnDefinition = "bytea")
    private byte[] portfolioFile;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private User employee;

    public EmployeeProfile() {}

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }

    public String getPortfolioPath() {
        return portfolioPath;
    }

    public void setPortfolioPath(String portfolioPath) {
        this.portfolioPath = portfolioPath;
    }

    public String getCvFileName() {
        return cvFileName;
    }

    public void setCvFileName(String cvFileName) {
        this.cvFileName = cvFileName;
    }

    public String getCvContentType() {
        return cvContentType;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public byte[] getCvFile() {
        return cvFile;
    }

    public void setCvFile(byte[] cvFile) {
        this.cvFile = cvFile;
    }

    public String getPortfolioFileName() {
        return portfolioFileName;
    }

    public void setPortfolioFileName(String portfolioFileName) {
        this.portfolioFileName = portfolioFileName;
    }

    public String getPortfolioContentType() {
        return portfolioContentType;
    }

    public void setPortfolioContentType(String portfolioContentType) {
        this.portfolioContentType = portfolioContentType;
    }

    public byte[] getPortfolioFile() {
        return portfolioFile;
    }

    public void setPortfolioFile(byte[] portfolioFile) {
        this.portfolioFile = portfolioFile;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }
}