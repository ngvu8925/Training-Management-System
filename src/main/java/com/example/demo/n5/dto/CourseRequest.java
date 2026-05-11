package com.example.demo.n5.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseRequest {
    @NotBlank(message = "Ma mon hoc khong duoc de trong")
    @Size(max = 50)
    private String code;

    @NotBlank(message = "Ten mon hoc khong duoc de trong")
    @Size(max = 255)
    private String name;

    private Boolean isActive;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
