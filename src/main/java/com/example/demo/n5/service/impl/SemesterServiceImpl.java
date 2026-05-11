package com.example.demo.n5.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.n5.dto.SemesterRequest;
import com.example.demo.n5.dto.SemesterResponse;
import com.example.demo.n5.model.entity.Semester;
import com.example.demo.n5.model.entity.SchoolYear;
import com.example.demo.n5.repository.SemesterRepository;
import com.example.demo.n5.repository.SchoolYearRepository;
import com.example.demo.n5.service.SemesterService;

@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository repository;
    private final SchoolYearRepository schoolYearRepository;

    public SemesterServiceImpl(SemesterRepository repository, SchoolYearRepository schoolYearRepository) {
        this.repository = repository;
        this.schoolYearRepository = schoolYearRepository;
    }

    @Override
    public List<SemesterResponse> getAll() {
        return repository.findByDeletedAtIsNullOrderByCreatedAtDesc().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public SemesterResponse getById(UUID id) {
        Semester entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Khong tim thay hoc ky"));
        if (entity.getDeletedAt() != null) {
            throw new RuntimeException("Hoc ky da bi xoa");
        }
        return mapToResponse(entity);
    }
    
    @Override
    public List<SemesterResponse> search(String keyword) {
        return repository.findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrDeletedAtIsNullAndNameContainingIgnoreCaseOrDeletedAtIsNullAndSchoolYearNameContainingIgnoreCaseOrderByCreatedAtDesc(keyword, keyword, keyword).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<SemesterResponse> getActive() {
        return repository.findByDeletedAtIsNullAndIsActiveTrueOrderByStartDateDesc()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SemesterResponse create(SemesterRequest request) {
        validateDateRange(request);
        Semester entity = new Semester();
        mapToEntity(request, entity);
        entity.setCreatedAt(LocalDateTime.now());
        
        SchoolYear schoolYear = getActiveSchoolYear(request.getSchoolYearId());
        entity.setSchoolYearName(schoolYear.getName());
        
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public SemesterResponse update(UUID id, SemesterRequest request) {
        validateDateRange(request);
        Semester entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Khong tim thay hoc ky"));
        if (entity.getDeletedAt() != null) {
            throw new RuntimeException("Hoc ky da bi xoa");
        }
        
        mapToEntity(request, entity);
        entity.setUpdatedAt(LocalDateTime.now());
        
        SchoolYear schoolYear = getActiveSchoolYear(request.getSchoolYearId());
        entity.setSchoolYearName(schoolYear.getName());
        
        return mapToResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Semester entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Khong tim thay hoc ky"));
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsActive(false);
        repository.save(entity);
    }

    private void mapToEntity(SemesterRequest request, Semester entity) {
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setSchoolYearId(request.getSchoolYearId());
        if (request.getSchoolYearName() != null && !request.getSchoolYearName().isBlank()) {
            entity.setSchoolYearName(request.getSchoolYearName());
        }
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        if (request.getCreatedBy() != null) entity.setCreatedBy(request.getCreatedBy());
        if (request.getUpdatedBy() != null) entity.setUpdatedBy(request.getUpdatedBy());
    }

    private SchoolYear getActiveSchoolYear(UUID schoolYearId) {
        SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay nam hoc"));
        if (schoolYear.getDeletedAt() != null || Boolean.FALSE.equals(schoolYear.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nam hoc khong con hoat dong");
        }
        return schoolYear;
    }

    private void validateDateRange(SemesterRequest request) {
        if (request.getStartDate() != null && request.getEndDate() != null && request.getStartDate().isAfter(request.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ngay bat dau hoc ky phai nho hon hoac bang ngay ket thuc");
        }
    }

    private SemesterResponse mapToResponse(Semester entity) {
        SemesterResponse response = new SemesterResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setSchoolYearId(entity.getSchoolYearId());
        response.setSchoolYearName(entity.getSchoolYearName());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setIsActive(entity.getIsActive());
        return response;
    }
}
