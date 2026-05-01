package com.example.demo.n5.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.n5.dto.StudentCourseSectionRequest;
import com.example.demo.n5.dto.StudentCourseSectionResponse;

public interface StudentCourseSectionService {
    List<StudentCourseSectionResponse> getAll();
    StudentCourseSectionResponse getById(UUID id);
    StudentCourseSectionResponse create(StudentCourseSectionRequest request);
    StudentCourseSectionResponse update(UUID id, StudentCourseSectionRequest request);
    void delete(UUID id);
    List<StudentCourseSectionResponse> search(String keyword);
    List<StudentCourseSectionResponse> getByCourseSectionId(UUID courseSectionId);
}
