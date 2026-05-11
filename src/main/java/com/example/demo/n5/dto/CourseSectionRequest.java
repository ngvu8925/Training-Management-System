package com.example.demo.n5.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourseSectionRequest {

    @NotBlank(message = "Ma lop hoc phan khong duoc de trong")
    @Size(max = 50)
    private String code;

    @NotBlank(message = "Ten lop hoc phan khong duoc de trong")
    @Size(max = 255)
    private String name;

    @NotNull(message = "ID mon hoc khong duoc de trong")
    private UUID courseId;

    @NotNull(message = "ID hoc ky khong duoc de trong")
    private UUID semesterId;

    @Size(max = 20)
    private String academicYear;

    private UUID employeeId;
    private UUID roomId;
    private UUID buildingId;
    
    @NotNull(message = "So sinh vien toi da khong duoc de trong")
    @Min(value = 1, message = "So sinh vien toi da phai lon hon 0")
    private Integer maxStudents;

    @Min(value = 0, message = "So sinh vien toi thieu khong duoc am")
    private Integer minStudents;
    
    @Size(max = 50)
    private String classType;
    
    @Size(max = 50)
    private String status;

    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    
    @Size(max = 255)
    private String note;
    
    private Boolean isActive;

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

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
