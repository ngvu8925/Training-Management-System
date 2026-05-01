package com.example.demo.n5.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LecturerCourseClassRequest {

    @NotNull(message = "Employee ID khong duoc de trong")
    private UUID employeeId;

    @NotNull(message = "Course Class ID khong duoc de trong")
    private UUID courseClassId;

    @Size(max = 50)
    private String role;

    private Boolean isActive;

    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }

    public UUID getCourseClassId() { return courseClassId; }
    public void setCourseClassId(UUID courseClassId) { this.courseClassId = courseClassId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
