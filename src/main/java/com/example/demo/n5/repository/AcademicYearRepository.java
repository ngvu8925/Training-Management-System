package com.example.demo.n5.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n5.model.entity.AcademicYear;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, UUID> {

    List<AcademicYear> findByDeletedAtIsNullOrderByCreatedAtDesc();

    Optional<AcademicYear> findByIdAndDeletedAtIsNull(UUID id);

    boolean existsByCodeIgnoreCaseAndDeletedAtIsNull(String code);

    boolean existsByYearAndDeletedAtIsNull(String year);

    boolean existsByCodeIgnoreCaseAndIdNotAndDeletedAtIsNull(String code, UUID id);

    boolean existsByYearAndIdNotAndDeletedAtIsNull(String year, UUID id);

    List<AcademicYear> findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);

    List<AcademicYear> findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);

    List<AcademicYear> findByDeletedAtIsNullAndYearContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);
}
