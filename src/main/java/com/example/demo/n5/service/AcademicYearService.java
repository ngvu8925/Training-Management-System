package com.example.demo.n5.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.n5.dto.AcademicYearRequest;
import com.example.demo.n5.dto.AcademicYearResponse;

public interface AcademicYearService {
    List<AcademicYearResponse> getAll();
    AcademicYearResponse getById(UUID id);
    AcademicYearResponse create(AcademicYearRequest request);
    AcademicYearResponse update(UUID id, AcademicYearRequest request);
    void delete(UUID id);
    List<AcademicYearResponse> search(String keyword);
}
