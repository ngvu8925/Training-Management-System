package com.example.demo.n5.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n5.dto.CourseRequest;
import com.example.demo.n5.dto.CourseResponse;
import com.example.demo.n5.model.entity.Course;
import com.example.demo.n5.repository.CourseRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    private final CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CourseResponse> getAll() {
        return repository.findByDeletedAtIsNullAndIsActiveTrueOrderByCodeAsc()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @PostMapping
    public CourseResponse create(@Valid @RequestBody CourseRequest request) {
        Course entity = new Course();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        return mapToResponse(repository.save(entity));
    }

    private CourseResponse mapToResponse(Course entity) {
        CourseResponse res = new CourseResponse();
        res.setId(entity.getId());
        res.setCode(entity.getCode());
        res.setName(entity.getName());
        res.setIsActive(entity.getIsActive());
        return res;
    }
}
