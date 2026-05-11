package com.example.demo.n5.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StudentCourseSectionRequest {

    @NotNull(message = "ID Sinh vien khong duoc de trong")
    private UUID studentId;

    @NotNull(message = "ID Lop hoc phan khong duoc de trong")
    private UUID courseSectionId;

    @Size(max = 50)
    private String status;

    private LocalDateTime registeredAt;

    @Size(max = 255)
    private String note;

    private Boolean isActive;

    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    public UUID getCourseSectionId() { return courseSectionId; }
    public void setCourseSectionId(UUID courseSectionId) { this.courseSectionId = courseSectionId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
