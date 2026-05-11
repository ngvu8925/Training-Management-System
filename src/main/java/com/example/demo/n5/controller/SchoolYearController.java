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

import com.example.demo.n5.dto.SchoolYearRequest;
import com.example.demo.n5.dto.SchoolYearResponse;
import com.example.demo.n5.service.SchoolYearService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/school-years")
@CrossOrigin
public class SchoolYearController {

    private final SchoolYearService service;

    public SchoolYearController(SchoolYearService service) {
        this.service = service;
    }

    @GetMapping
    public List<SchoolYearResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SchoolYearResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public List<SchoolYearResponse> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolYearResponse create(@Valid @RequestBody SchoolYearRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public SchoolYearResponse update(@PathVariable UUID id, @Valid @RequestBody SchoolYearRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
