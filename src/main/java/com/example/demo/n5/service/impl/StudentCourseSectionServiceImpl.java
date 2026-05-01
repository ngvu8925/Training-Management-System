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

import com.example.demo.n5.dto.StudentCourseSectionRequest;
import com.example.demo.n5.dto.StudentCourseSectionResponse;
import com.example.demo.n5.model.entity.CourseSection;
import com.example.demo.n5.model.entity.StudentCourseSection;
import com.example.demo.n5.repository.CourseSectionRepository;
import com.example.demo.n5.repository.StudentCourseSectionRepository;
import com.example.demo.n5.service.StudentCourseSectionService;

@Service
public class StudentCourseSectionServiceImpl implements StudentCourseSectionService {

    private final StudentCourseSectionRepository repository;
    private final CourseSectionRepository courseSectionRepository;

    public StudentCourseSectionServiceImpl(StudentCourseSectionRepository repository, CourseSectionRepository courseSectionRepository) {
        this.repository = repository;
        this.courseSectionRepository = courseSectionRepository;
    }

    @Override
    public List<StudentCourseSectionResponse> getAll() {
        return repository.findByDeletedAtIsNullOrderByCreatedAtDesc().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public StudentCourseSectionResponse getById(UUID id) {
        StudentCourseSection entity = repository.findById(id)
            .filter(item -> item.getDeletedAt() == null)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay ghi danh"));
        return mapToResponse(entity);
    }
    
    @Override
    public List<StudentCourseSectionResponse> search(String keyword) {
        // Fallback to all if search not implemented in repo
        return getAll();
    }

    @Override
    @Transactional
    public StudentCourseSectionResponse create(StudentCourseSectionRequest request) {
        CourseSection courseSection = getAvailableCourseSection(request.getCourseSectionId());
        if (repository.existsByStudentIdAndCourseSectionIdAndDeletedAtIsNull(request.getStudentId(), request.getCourseSectionId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sinh vien da co trong lop hoc phan");
        }
        long currentStudents = repository.countByCourseSectionIdAndDeletedAtIsNullAndIsActiveTrue(request.getCourseSectionId());
        if (courseSection.getMaxStudents() != null && currentStudents >= courseSection.getMaxStudents()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lop hoc phan da dat so sinh vien toi da");
        }

        StudentCourseSection entity = new StudentCourseSection();
        entity.setStudentId(request.getStudentId());
        entity.setCourseSectionId(request.getCourseSectionId());
        entity.setStatus(normalizeStatus(request.getStatus(), "registered"));
        entity.setNote(request.getNote());
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setRegisteredAt(request.getRegisteredAt() != null ? request.getRegisteredAt() : LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public StudentCourseSectionResponse update(UUID id, StudentCourseSectionRequest request) {
        StudentCourseSection entity = repository.findById(id)
            .filter(item -> item.getDeletedAt() == null)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay ghi danh"));
        CourseSection courseSection = getAvailableCourseSection(request.getCourseSectionId());
        if ((!Objects.equals(entity.getStudentId(), request.getStudentId()) || !Objects.equals(entity.getCourseSectionId(), request.getCourseSectionId()))
            && repository.existsByStudentIdAndCourseSectionIdAndDeletedAtIsNull(request.getStudentId(), request.getCourseSectionId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sinh vien da co trong lop hoc phan");
        }
        if (!Objects.equals(entity.getCourseSectionId(), request.getCourseSectionId())) {
            long currentStudents = repository.countByCourseSectionIdAndDeletedAtIsNullAndIsActiveTrue(request.getCourseSectionId());
            if (courseSection.getMaxStudents() != null && currentStudents >= courseSection.getMaxStudents()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lop hoc phan da dat so sinh vien toi da");
            }
        }
        entity.setStudentId(request.getStudentId());
        entity.setCourseSectionId(request.getCourseSectionId());
        entity.setStatus(normalizeStatus(request.getStatus(), entity.getStatus()));
        entity.setRegisteredAt(request.getRegisteredAt() != null ? request.getRegisteredAt() : entity.getRegisteredAt());
        entity.setNote(request.getNote());
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        StudentCourseSection entity = repository.findById(id)
            .filter(item -> item.getDeletedAt() == null)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay ghi danh"));
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsActive(false);
        repository.save(entity);
    }

    @Override
    public List<StudentCourseSectionResponse> getByCourseSectionId(UUID courseSectionId) {
        return repository.findByCourseSectionIdAndDeletedAtIsNull(courseSectionId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private StudentCourseSectionResponse mapToResponse(StudentCourseSection entity) {
        StudentCourseSectionResponse response = new StudentCourseSectionResponse();
        response.setId(entity.getId());
        response.setStudentId(entity.getStudentId());
        response.setCourseSectionId(entity.getCourseSectionId());
        response.setStatus(entity.getStatus());
        response.setRegisteredAt(entity.getRegisteredAt());
        response.setNote(entity.getNote());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setIsActive(entity.getIsActive());
        return response;
    }

    private CourseSection getAvailableCourseSection(UUID courseSectionId) {
        CourseSection courseSection = courseSectionRepository.findByIdAndDeletedAtIsNull(courseSectionId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay lop hoc phan"));
        String status = courseSection.getStatus() == null ? "" : courseSection.getStatus().trim().toLowerCase();
        if (Boolean.FALSE.equals(courseSection.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lop hoc phan dang bi khoa (ngung hieu luc)");
        }
        return courseSection;
    }

    private String normalizeStatus(String status, String defaultStatus) {
        if (status == null || status.isBlank()) {
            return defaultStatus;
        }
        return status.trim().toLowerCase();
    }
}
