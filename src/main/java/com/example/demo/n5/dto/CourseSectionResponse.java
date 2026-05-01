package com.example.demo.n5.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CourseSectionResponse {

    private UUID id;
    private String code;
    private String name;
    private UUID courseId;
    private UUID semesterId;
    private String academicYear;
    private UUID employeeId;
    private UUID roomId;
    private UUID buildingId;
    private Integer maxStudents;
    private Integer minStudents;
    private String classType;
    private String status;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }

    public UUID getSemesterId() { return semesterId; }
    public void setSemesterId(UUID semesterId) { this.semesterId = semesterId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }

    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }

    public UUID getBuildingId() { return buildingId; }
    public void setBuildingId(UUID buildingId) { this.buildingId = buildingId; }

    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }

    public Integer getMinStudents() { return minStudents; }
    public void setMinStudents(Integer minStudents) { this.minStudents = minStudents; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRegistrationStart() { return registrationStart; }
    public void setRegistrationStart(LocalDateTime registrationStart) { this.registrationStart = registrationStart; }

    public LocalDateTime getRegistrationEnd() { return registrationEnd; }
    public void setRegistrationEnd(LocalDateTime registrationEnd) { this.registrationEnd = registrationEnd; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
