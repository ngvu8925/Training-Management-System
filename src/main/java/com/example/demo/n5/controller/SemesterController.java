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

import com.example.demo.n5.dto.SemesterRequest;
import com.example.demo.n5.dto.SemesterResponse;
import com.example.demo.n5.service.SemesterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/semesters")
@CrossOrigin
public class SemesterController {

    private final SemesterService service;

    public SemesterController(SemesterService service) {
        this.service = service;
    }

    @GetMapping
    public List<SemesterResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<SemesterResponse> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @GetMapping("/active")
    public List<SemesterResponse> getActive() {
        return service.getActive();
    }

    @GetMapping("/{id}")
    public SemesterResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SemesterResponse create(@Valid @RequestBody SemesterRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public SemesterResponse update(@PathVariable UUID id, @Valid @RequestBody SemesterRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
