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

import com.example.demo.n5.dto.AcademicYearRequest;
import com.example.demo.n5.dto.AcademicYearResponse;
import com.example.demo.n5.service.AcademicYearService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/academic-years")
@CrossOrigin
public class AcademicYearController {

    private final AcademicYearService service;

    public AcademicYearController(AcademicYearService service) {
        this.service = service;
    }

    @GetMapping
    public List<AcademicYearResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AcademicYearResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public List<AcademicYearResponse> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AcademicYearResponse create(@Valid @RequestBody AcademicYearRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public AcademicYearResponse update(@PathVariable UUID id, @Valid @RequestBody AcademicYearRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
