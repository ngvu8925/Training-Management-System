package com.example.demo.n5.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n5.model.entity.SchoolYear;

public interface SchoolYearRepository extends JpaRepository<SchoolYear, UUID> {

    List<SchoolYear> findByDeletedAtIsNullOrderByCreatedAtDesc();

    Optional<SchoolYear> findByIdAndDeletedAtIsNull(UUID id);

    boolean existsByCodeIgnoreCaseAndDeletedAtIsNull(String code);

    boolean existsByNameIgnoreCaseAndDeletedAtIsNull(String name);

    boolean existsByCodeIgnoreCaseAndIdNotAndDeletedAtIsNull(String code, UUID id);

    boolean existsByNameIgnoreCaseAndIdNotAndDeletedAtIsNull(String name, UUID id);

    List<SchoolYear> findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);

    List<SchoolYear> findByDeletedAtIsNullAndNameContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);

    List<SchoolYear> findByDeletedAtIsNullAndDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(String keyword);
}
