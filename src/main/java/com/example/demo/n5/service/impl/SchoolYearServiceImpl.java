package com.example.demo.n5.service.impl;

import java.time.LocalDate;
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

import com.example.demo.n5.dto.SchoolYearRequest;
import com.example.demo.n5.dto.SchoolYearResponse;
import com.example.demo.n5.model.entity.SchoolYear;
import com.example.demo.n5.repository.SchoolYearRepository;
import com.example.demo.n5.service.SchoolYearService;

@Service
public class SchoolYearServiceImpl implements SchoolYearService {

    private final SchoolYearRepository repository;

    public SchoolYearServiceImpl(SchoolYearRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SchoolYearResponse> getAll() {
        return repository.findByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    public SchoolYearResponse getById(UUID id) {
        return toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public SchoolYearResponse create(SchoolYearRequest request) {
        validateDateRange(request.getStartDate(), request.getEndDate());

        String code = request.getCode().trim();
        String name = request.getName().trim();

        if (repository.existsByCodeIgnoreCaseAndDeletedAtIsNull(code)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ma nam hoc da ton tai");
        }
        if (repository.existsByNameIgnoreCaseAndDeletedAtIsNull(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ten nam hoc da ton tai");
        }

        SchoolYear entity = new SchoolYear();
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
    public SchoolYearResponse update(UUID id, SchoolYearRequest request) {
        validateDateRange(request.getStartDate(), request.getEndDate());

        SchoolYear entity = getEntity(id);
        String code = request.getCode().trim();
        String name = request.getName().trim();

        if (repository.existsByCodeIgnoreCaseAndIdNotAndDeletedAtIsNull(code, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ma nam hoc da ton tai");
        }
        if (repository.existsByNameIgnoreCaseAndIdNotAndDeletedAtIsNull(name, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ten nam hoc da ton tai");
        }

        mapRequestToEntity(entity, request);
        entity.setUpdatedAt(LocalDateTime.now());

        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SchoolYear entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setIsActive(Boolean.FALSE);
        repository.save(entity);
    }

    @Override
    public List<SchoolYearResponse> search(String keyword) {
        String normalized = keyword == null ? "" : keyword.trim();
        if (normalized.isEmpty()) {
            return getAll();
        }

        Map<UUID, SchoolYear> unique = new LinkedHashMap<>();
        addAll(unique, repository.findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrderByCreatedAtDesc(normalized));
        addAll(unique, repository.findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByCreatedAtDesc(normalized));
        addAll(unique, repository.findByDeletedAtIsNullAndDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(normalized));

        return new ArrayList<>(unique.values())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private void addAll(Map<UUID, SchoolYear> target, List<SchoolYear> source) {
        for (SchoolYear item : source) {
            target.putIfAbsent(item.getId(), item);
        }
    }

    private SchoolYear getEntity(UUID id) {
        return repository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay nam hoc"));
    }

    private void mapRequestToEntity(SchoolYear entity, SchoolYearRequest request) {
        entity.setCode(request.getCode().trim());
        entity.setName(request.getName().trim());
        entity.setDescription(blankToNull(request.getDescription()));
        entity.setNote(blankToNull(request.getNote()));
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setCreatedBy(request.getCreatedBy());
        entity.setUpdatedBy(request.getUpdatedBy());
        if (request.getIsActive() != null) {
            entity.setIsActive(request.getIsActive());
        }
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ngay bat dau phai nho hon hoac bang ngay ket thuc");
        }
    }

    private String blankToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private SchoolYearResponse toResponse(SchoolYear entity) {
        SchoolYearResponse response = new SchoolYearResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setNote(entity.getNote());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setIsActive(entity.getIsActive());
        return response;
    }
}
