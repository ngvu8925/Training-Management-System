package com.example.demo.n5.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SchoolYearRequest {

    @NotBlank(message = "Ma nam hoc khong duoc de trong")
    @Size(max = 100, message = "Ma nam hoc toi da 100 ky tu")
    private String code;

    @NotBlank(message = "Ten nam hoc khong duoc de trong")
    @Size(max = 255, message = "Ten nam hoc toi da 255 ky tu")
    private String name;

    @Size(max = 255, message = "Mo ta toi da 255 ky tu")
    private String description;

    @Size(max = 255, message = "Ghi chu toi da 255 ky tu")
    private String note;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
