package com.example.demo.n5.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.n5.dto.LecturerCourseClassRequest;
import com.example.demo.n5.dto.LecturerCourseClassResponse;

public interface LecturerCourseClassService {
    List<LecturerCourseClassResponse> getAll();
    LecturerCourseClassResponse getById(UUID id);
    LecturerCourseClassResponse create(LecturerCourseClassRequest request);
    LecturerCourseClassResponse update(UUID id, LecturerCourseClassRequest request);
    void delete(UUID id);
    List<LecturerCourseClassResponse> search(String keyword);
    List<LecturerCourseClassResponse> getByCourseClassId(UUID courseClassId);
    List<LecturerCourseClassResponse> getSchedule(UUID employeeId, UUID semesterId);
}
