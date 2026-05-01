package com.example.demo.n5.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.n5.dto.CourseSectionRequest;
import com.example.demo.n5.dto.CourseSectionResponse;
import com.example.demo.n5.model.entity.CourseSection;
import com.example.demo.n5.model.entity.Semester;
import com.example.demo.n5.repository.CourseSectionRepository;
import com.example.demo.n5.repository.SemesterRepository;
import com.example.demo.n5.service.CourseSectionService;

@Service
public class CourseSectionServiceImpl implements CourseSectionService {

    private static final Set<String> VALID_STATUSES = Set.of("planned", "open", "closed", "canceled");
    private static final Map<String, Set<String>> STATUS_TRANSITIONS = Map.of(
        "planned", Set.of("open", "canceled"),
        "open", Set.of("closed", "canceled"),
        "closed", Set.of(),
        "canceled", Set.of()
    );

    private final CourseSectionRepository repository;
    private final SemesterRepository semesterRepository;

    public CourseSectionServiceImpl(CourseSectionRepository repository, SemesterRepository semesterRepository) {
        this.repository = repository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    public List<CourseSectionResponse> getAll() {
        return repository.findByDeletedAtIsNullOrderByCreatedAtDesc().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public CourseSectionResponse getById(UUID id) {
        CourseSection entity = getEntity(id);
        return mapToResponse(entity);
    }
    
    @Override
    public List<CourseSectionResponse> search(String keyword) {
        return repository.findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrderByCreatedAtDesc(keyword).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CourseSectionResponse> filter(UUID semesterId, UUID courseId, String status) {
        String normalizedStatus = normalizeStatus(status);
        return repository.filter(semesterId, courseId, normalizedStatus)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseSectionResponse create(CourseSectionRequest request) {
        validateRequest(request);
        CourseSection entity = new CourseSection();
        mapToEntity(request, entity);
        entity.setCreatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public CourseSectionResponse update(UUID id, CourseSectionRequest request) {
        validateRequest(request);
        CourseSection entity = getEntity(id);
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            request.setStatus(entity.getStatus() == null || entity.getStatus().isBlank() ? "planned" : entity.getStatus());
        }
        validateStatusTransition(normalizeStatus(entity.getStatus()), normalizeRequiredStatus(request.getStatus()));
        mapToEntity(request, entity);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public CourseSectionResponse changeStatus(UUID id, String status) {
        CourseSection entity = getEntity(id);
        String currentStatus = normalizeStatus(entity.getStatus());
        String nextStatus = normalizeRequiredStatus(status);

        if (currentStatus == null) {
            currentStatus = "planned";
        }
        validateStatusTransition(currentStatus, nextStatus);

        entity.setStatus(nextStatus);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CourseSection entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsActive(false);
        repository.save(entity);
    }

    private void mapToEntity(CourseSectionRequest request, CourseSection entity) {
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setCourseId(request.getCourseId());
        entity.setSemesterId(request.getSemesterId());
        entity.setAcademicYear(request.getAcademicYear());
        entity.setEmployeeId(request.getEmployeeId());
        entity.setRoomId(request.getRoomId());
        entity.setBuildingId(request.getBuildingId());
        entity.setClassType(request.getClassType());
        entity.setMaxStudents(request.getMaxStudents());
        entity.setMinStudents(request.getMinStudents());
        entity.setStatus(normalizeStatus(request.getStatus()) != null ? normalizeStatus(request.getStatus()) : "planned");
        entity.setRegistrationStart(request.getRegistrationStart());
        entity.setRegistrationEnd(request.getRegistrationEnd());
        entity.setNote(request.getNote());
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
    }

    private CourseSection getEntity(UUID id) {
        return repository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay lop hoc phan"));
    }

    private void validateRequest(CourseSectionRequest request) {
        if (request.getRegistrationStart() != null && request.getRegistrationEnd() != null
            && !request.getRegistrationStart().isBefore(request.getRegistrationEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thoi gian bat dau dang ky phai nho hon thoi gian ket thuc");
        }
        if (request.getMaxStudents() == null || request.getMaxStudents() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "So sinh vien toi da phai lon hon 0");
        }
        if (request.getMinStudents() != null && request.getMinStudents() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "So sinh vien toi thieu khong duoc am");
        }
        if (request.getMinStudents() != null && request.getMinStudents() > request.getMaxStudents()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "So sinh vien toi thieu khong duoc lon hon toi da");
        }
        if (request.getCourseId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID mon hoc khong duoc de trong");
        }
        if (request.getSemesterId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID hoc ky khong duoc de trong");
        }
        Semester semester = semesterRepository.findById(request.getSemesterId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay hoc ky"));
        if (semester.getDeletedAt() != null || Boolean.FALSE.equals(semester.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hoc ky khong con hoat dong");
        }
        normalizeRequiredStatus(request.getStatus() == null || request.getStatus().isBlank() ? "planned" : request.getStatus());
    }

    private String normalizeRequiredStatus(String status) {
        String normalized = normalizeStatus(status);
        if (normalized == null || !VALID_STATUSES.contains(normalized)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trang thai lop hoc phan khong hop le");
        }
        return normalized;
    }

    private String normalizeStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        return status.trim().toLowerCase();
    }

    private void validateStatusTransition(String currentStatus, String nextStatus) {
        String current = currentStatus == null ? "planned" : currentStatus;
        if (!current.equals(nextStatus) && !STATUS_TRANSITIONS.getOrDefault(current, Set.of()).contains(nextStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Khong the chuyen trang thai tu " + current + " sang " + nextStatus);
        }
    }

    private CourseSectionResponse mapToResponse(CourseSection entity) {
        CourseSectionResponse response = new CourseSectionResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(resolveDisplayName(entity));
        response.setCourseId(entity.getCourseId());
        response.setSemesterId(entity.getSemesterId());
        response.setAcademicYear(entity.getAcademicYear());
        response.setEmployeeId(entity.getEmployeeId());
        response.setRoomId(entity.getRoomId());
        response.setBuildingId(entity.getBuildingId());
        response.setClassType(entity.getClassType());
        response.setMaxStudents(entity.getMaxStudents());
        response.setMinStudents(entity.getMinStudents());
        response.setStatus(entity.getStatus());
        response.setRegistrationStart(entity.getRegistrationStart());
        response.setRegistrationEnd(entity.getRegistrationEnd());
        response.setNote(entity.getNote());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setIsActive(entity.getIsActive());
        return response;
    }

    private String resolveDisplayName(CourseSection entity) {
        if (entity.getName() != null && !entity.getName().isBlank()) {
            return entity.getName();
        }
        if (entity.getCode() != null && !entity.getCode().isBlank()) {
            return "Lớp HP " + entity.getCode();
        }
        return "Lớp học phần";
    }
}
