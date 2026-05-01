package com.example.demo.n5.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class SemesterRequest {

    @NotBlank(message = "Ma hoc ky khong duoc de trong")
    @Size(max = 100, message = "Ma hoc ky toi da 100 ky tu")
    private String code;

    @NotBlank(message = "Ten hoc ky khong duoc de trong")
    @Size(max = 255, message = "Ten hoc ky toi da 255 ky tu")
    private String name;

    @NotNull(message = "Nam hoc khong duoc de trong")
    private UUID schoolYearId;

    private String schoolYearName;

    private LocalDate startDate;
    private LocalDate endDate;
    private UUID createdBy;
    private UUID updatedBy;
    private Boolean isActive;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(UUID schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public String getSchoolYearName() {
        return schoolYearName;
    }

    public void setSchoolYearName(String schoolYearName) {
        this.schoolYearName = schoolYearName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
