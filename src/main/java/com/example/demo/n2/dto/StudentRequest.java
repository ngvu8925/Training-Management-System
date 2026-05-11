package com.example.demo.n2.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentRequest {

    private UUID userId;

    @NotBlank(message = "Ma sinh vien khong duoc de trong")
    @Size(max = 20, message = "Ma sinh vien toi da 20 ky tu")
    private String code;

    @NotBlank(message = "Ho ten khong duoc de trong")
    @Size(max = 100, message = "Ho ten toi da 100 ky tu")
    private String fullname;

    private LocalDate dateOfBirth;

    @Size(max = 10, message = "Gioi tinh toi da 10 ky tu")
    private String gender;

    @Size(max = 20, message = "CCCD/CMND toi da 20 ky tu")
    private String personalIdentificationNumber;

    private LocalDate dateOfIssue;

    @Size(max = 100, message = "Noi cap toi da 100 ky tu")
    private String cardPlace;

    @Size(max = 300, message = "Dia chi thuong tru toi da 300 ky tu")
    private String address;

    @Size(max = 300, message = "Dia chi hien tai toi da 300 ky tu")
    private String currentAddress;

    private UUID academicYearYear;
    private UUID departmentId;
    private UUID majorId;
    private UUID trainingProgramId;

    @Size(max = 50, message = "Trang thai toi da 50 ky tu")
    private String status;

    private UUID studentClasseId;
    private LocalDate admissionYear;
    private UUID createdBy;
    private UUID updatedBy;
    private Boolean isActive;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalIdentificationNumber() {
        return personalIdentificationNumber;
    }

    public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getCardPlace() {
        return cardPlace;
    }

    public void setCardPlace(String cardPlace) {
        this.cardPlace = cardPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public UUID getAcademicYearYear() {
        return academicYearYear;
    }

    public void setAcademicYearYear(UUID academicYearYear) {
        this.academicYearYear = academicYearYear;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public UUID getMajorId() {
        return majorId;
    }

    public void setMajorId(UUID majorId) {
        this.majorId = majorId;
    }

    public UUID getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(UUID trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getStudentClasseId() {
        return studentClasseId;
    }

    public void setStudentClasseId(UUID studentClasseId) {
        this.studentClasseId = studentClasseId;
    }

    public LocalDate getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(LocalDate admissionYear) {
        this.admissionYear = admissionYear;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
