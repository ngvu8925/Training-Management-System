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

import com.example.demo.n5.dto.CourseSectionRequest;
import com.example.demo.n5.dto.CourseSectionResponse;
import com.example.demo.n5.service.CourseSectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course-sections")
@CrossOrigin
public class CourseSectionController {

    private final CourseSectionService service;

    public CourseSectionController(CourseSectionService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseSectionResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<CourseSectionResponse> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @GetMapping("/filter")
    public List<CourseSectionResponse> filter(
            @RequestParam(required = false) UUID semesterId,
            @RequestParam(required = false) UUID courseId,
            @RequestParam(required = false) String status) {
        return service.filter(semesterId, courseId, status);
    }

    @GetMapping("/{id}")
    public CourseSectionResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseSectionResponse create(@Valid @RequestBody CourseSectionRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CourseSectionResponse update(@PathVariable UUID id, @Valid @RequestBody CourseSectionRequest request) {
        return service.update(id, request);
    }

    @PutMapping("/{id}/status")
    public CourseSectionResponse changeStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
