package com.example.demo.n5.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.n5.dto.LecturerCourseClassRequest;
import com.example.demo.n5.dto.LecturerCourseClassResponse;
import com.example.demo.n5.model.entity.CourseSection;
import com.example.demo.n5.model.entity.LecturerCourseClass;
import com.example.demo.n5.repository.CourseSectionRepository;
import com.example.demo.n5.repository.LecturerCourseClassRepository;
import com.example.demo.n5.service.LecturerCourseClassService;

@Service
public class LecturerCourseClassServiceImpl implements LecturerCourseClassService {

    private final LecturerCourseClassRepository repository;
    private final CourseSectionRepository courseSectionRepository;

    public LecturerCourseClassServiceImpl(LecturerCourseClassRepository repository, CourseSectionRepository courseSectionRepository) {
        this.repository = repository;
        this.courseSectionRepository = courseSectionRepository;
    }

    @Override
    public List<LecturerCourseClassResponse> getAll() {
        return repository.findByDeletedAtIsNullOrderByCreatedAtDesc().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public LecturerCourseClassResponse getById(UUID id) {
        return repository.findById(id)
            .filter(item -> item.getDeletedAt() == null)
            .map(this::mapToResponse)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay phan cong giang vien"));
    }
    
    @Override
    public List<LecturerCourseClassResponse> search(String keyword) {
        return getAll();
    }

    @Override
    @Transactional
    public LecturerCourseClassResponse create(LecturerCourseClassRequest request) {
        getCourseSection(request.getCourseClassId());
        if (repository.existsByEmployeeIdAndCourseClassIdAndDeletedAtIsNull(request.getEmployeeId(), request.getCourseClassId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Giang vien da duoc phan cong vao lop nay");
        }
        LecturerCourseClass entity = new LecturerCourseClass();
        entity.setEmployeeId(request.getEmployeeId());
        entity.setCourseClassId(request.getCourseClassId());
        entity.setRole(normalizeRole(request.getRole()));
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        entity.setCreatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public LecturerCourseClassResponse update(UUID id, LecturerCourseClassRequest request) {
        LecturerCourseClass entity = repository.findById(id)
            .filter(item -> item.getDeletedAt() == null)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay phan cong giang vien"));
        getCourseSection(request.getCourseClassId());
        if ((!Objects.equals(entity.getEmployeeId(), request.getEmployeeId()) || !Objects.equals(entity.getCourseClassId(), request.getCourseClassId()))
            && repository.existsByEmployeeIdAndCourseClassIdAndDeletedAtIsNull(request.getEmployeeId(), request.getCourseClassId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Giang vien da duoc phan cong vao lop nay");
        }
        entity.setEmployeeId(request.getEmployeeId());
        entity.setCourseClassId(request.getCourseClassId());
        entity.setRole(normalizeRole(request.getRole()));
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        LecturerCourseClass entity = repository.findById(id)
            .filter(item -> item.getDeletedAt() == null)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay phan cong giang vien"));
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsActive(false);
        repository.save(entity);
    }

    @Override
    public List<LecturerCourseClassResponse> getByCourseClassId(UUID courseClassId) {
        return repository.findByCourseClassIdAndDeletedAtIsNull(courseClassId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<LecturerCourseClassResponse> getSchedule(UUID employeeId, UUID semesterId) {
        return repository.findSchedule(employeeId, semesterId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private LecturerCourseClassResponse mapToResponse(LecturerCourseClass entity) {
        LecturerCourseClassResponse response = new LecturerCourseClassResponse();
        response.setId(entity.getId());
        response.setEmployeeId(entity.getEmployeeId());
        response.setCourseClassId(entity.getCourseClassId());
        response.setRole(entity.getRole());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setIsActive(entity.getIsActive());
        return response;
    }

    private CourseSection getCourseSection(UUID courseClassId) {
        return courseSectionRepository.findByIdAndDeletedAtIsNull(courseClassId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay lop hoc phan"));
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "lecturer";
        }
        return role.trim().toLowerCase();
    }
}
