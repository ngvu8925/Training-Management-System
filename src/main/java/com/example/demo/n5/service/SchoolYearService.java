package com.example.demo.n5.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.n5.dto.SchoolYearRequest;
import com.example.demo.n5.dto.SchoolYearResponse;

public interface SchoolYearService {
    List<SchoolYearResponse> getAll();
    SchoolYearResponse getById(UUID id);
    SchoolYearResponse create(SchoolYearRequest request);
    SchoolYearResponse update(UUID id, SchoolYearRequest request);
    void delete(UUID id);
    List<SchoolYearResponse> search(String keyword);
}
