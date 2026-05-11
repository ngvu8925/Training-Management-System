package com.example.demo.n5.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.n5.dto.CourseSectionRequest;
import com.example.demo.n5.dto.CourseSectionResponse;

public interface CourseSectionService {
    List<CourseSectionResponse> getAll();
    CourseSectionResponse getById(UUID id);
    CourseSectionResponse create(CourseSectionRequest request);
    CourseSectionResponse update(UUID id, CourseSectionRequest request);
    void delete(UUID id);
    List<CourseSectionResponse> search(String keyword);
    List<CourseSectionResponse> filter(UUID semesterId, UUID courseId, String status);
    CourseSectionResponse changeStatus(UUID id, String status);
}
