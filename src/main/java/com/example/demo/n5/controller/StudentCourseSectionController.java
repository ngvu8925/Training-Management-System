package com.example.demo.n5.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n5.dto.StudentCourseSectionRequest;
import com.example.demo.n5.dto.StudentCourseSectionResponse;
import com.example.demo.n5.service.StudentCourseSectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student-course-sections")
@CrossOrigin
public class StudentCourseSectionController {

    private final StudentCourseSectionService service;

    public StudentCourseSectionController(StudentCourseSectionService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentCourseSectionResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/course-section/{courseSectionId}")
    public List<StudentCourseSectionResponse> getByCourseSection(@PathVariable UUID courseSectionId) {
        return service.getByCourseSectionId(courseSectionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCourseSectionResponse create(@Valid @RequestBody StudentCourseSectionRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public StudentCourseSectionResponse update(@PathVariable UUID id, @Valid @RequestBody StudentCourseSectionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
