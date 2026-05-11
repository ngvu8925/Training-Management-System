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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n5.dto.LecturerCourseClassRequest;
import com.example.demo.n5.dto.LecturerCourseClassResponse;
import com.example.demo.n5.service.LecturerCourseClassService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/lecturer-course-classes")
@CrossOrigin
public class LecturerCourseClassController {

    private final LecturerCourseClassService service;

    public LecturerCourseClassController(LecturerCourseClassService service) {
        this.service = service;
    }

    @GetMapping
    public List<LecturerCourseClassResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/course-class/{courseClassId}")
    public List<LecturerCourseClassResponse> getByCourseClass(@PathVariable UUID courseClassId) {
        return service.getByCourseClassId(courseClassId);
    }

    @GetMapping("/schedule")
    public List<LecturerCourseClassResponse> getSchedule(@RequestParam UUID employeeId, @RequestParam UUID semesterId) {
        return service.getSchedule(employeeId, semesterId);
    }

    @GetMapping("/{id}")
    public LecturerCourseClassResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LecturerCourseClassResponse create(@Valid @RequestBody LecturerCourseClassRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public LecturerCourseClassResponse update(@PathVariable UUID id, @Valid @RequestBody LecturerCourseClassRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
