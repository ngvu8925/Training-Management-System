package com.example.demo.n5.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.n5.dto.SemesterRequest;
import com.example.demo.n5.dto.SemesterResponse;

public interface SemesterService {
    List<SemesterResponse> getAll();
    SemesterResponse getById(UUID id);
    SemesterResponse create(SemesterRequest request);
    SemesterResponse update(UUID id, SemesterRequest request);
    void delete(UUID id);
    List<SemesterResponse> search(String keyword);
    List<SemesterResponse> getActive();
}
