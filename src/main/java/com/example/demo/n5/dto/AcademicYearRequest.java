package com.example.demo.n5.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AcademicYearRequest {

    @NotBlank(message = "Ma nien khoa khong duoc de trong")
    @Size(max = 50, message = "Ma nien khoa toi da 50 ky tu")
    private String code;

    @NotBlank(message = "Ten nien khoa khong duoc de trong")
    @Size(max = 100, message = "Ten nien khoa toi da 100 ky tu")
    private String name;

    @NotBlank(message = "Nam hoc khong duoc de trong")
    @Size(max = 20, message = "Nam hoc toi da 20 ky tu")
    private String year;

    @Size(max = 255, message = "Mo ta toi da 255 ky tu")
    private String description;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
