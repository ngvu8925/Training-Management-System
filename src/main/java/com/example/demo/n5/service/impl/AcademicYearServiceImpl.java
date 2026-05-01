package com.example.demo.n5.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.n5.dto.AcademicYearRequest;
import com.example.demo.n5.dto.AcademicYearResponse;
import com.example.demo.n5.model.entity.AcademicYear;
import com.example.demo.n5.repository.AcademicYearRepository;
import com.example.demo.n5.service.AcademicYearService;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository repository;

    public AcademicYearServiceImpl(AcademicYearRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AcademicYearResponse> getAll() {
        return repository.findByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public AcademicYearResponse getById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public AcademicYearResponse create(AcademicYearRequest request) {
        validateDateRange(request.getStartDate(), request.getEndDate());

        String code = request.getCode().trim();
        String year = request.getYear().trim();

        if (repository.existsByCodeIgnoreCaseAndDeletedAtIsNull(code)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ma nien khoa da ton tai");
        }
        if (repository.existsByYearAndDeletedAtIsNull(year)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nam nien khoa da ton tai");
        }

        AcademicYear entity = new AcademicYear();
        mapRequestToEntity(entity, request);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setDeletedAt(null);
        entity.setDeletedBy(null);
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE);

        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public AcademicYearResponse update(UUID id, AcademicYearRequest request) {
        validateDateRange(request.getStartDate(), request.getEndDate());

        AcademicYear entity = getEntity(id);
        String code = request.getCode().trim();
        String year = request.getYear().trim();

        if (repository.existsByCodeIgnoreCaseAndIdNotAndDeletedAtIsNull(code, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ma nien khoa da ton tai");
        }
        if (repository.existsByYearAndIdNotAndDeletedAtIsNull(year, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nam nien khoa da ton tai");
        }

        mapRequestToEntity(entity, request);
        entity.setUpdatedAt(LocalDateTime.now());

        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AcademicYear entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsActive(Boolean.FALSE);
        repository.save(entity);
    }

    @Override
    public List<AcademicYearResponse> search(String keyword) {
        String normalized = keyword == null ? "" : keyword.trim();
        if (normalized.isEmpty()) {
            return getAll();
        }

        Map<UUID, AcademicYear> unique = new LinkedHashMap<>();
        addAll(unique, repository.findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrderByCreatedAtDesc(normalized));
        addAll(unique, repository.findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByCreatedAtDesc(normalized));
        addAll(unique, repository.findByDeletedAtIsNullAndYearContainingIgnoreCaseOrderByCreatedAtDesc(normalized));

        return new ArrayList<>(unique.values())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private void addAll(Map<UUID, AcademicYear> target, List<AcademicYear> source) {
        for (AcademicYear item : source) {
            target.putIfAbsent(item.getId(), item);
        }
    }

    private AcademicYear getEntity(UUID id) {
        return repository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay nien khoa"));
    }

    private void mapRequestToEntity(AcademicYear entity, AcademicYearRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setYear(request.getYear().trim());
        entity.setDescription(request.getDescription());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setCreatedBy(request.getCreatedBy());
        entity.setUpdatedBy(request.getUpdatedBy());
        if (request.getIsActive() != null) {
            entity.setIsActive(request.getIsActive());
        }
    }

    private void validateDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ngay bat dau phai nho hon hoac bang ngay ket thuc");
        }
    }

    private AcademicYearResponse toResponse(AcademicYear entity) {
        AcademicYearResponse response = new AcademicYearResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setYear(entity.getYear());
        response.setDescription(entity.getDescription());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setIsActive(entity.getIsActive());
        return response;
    }
}
