package com.example.demo.n5.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n5.model.entity.Semester;

public interface SemesterRepository extends JpaRepository<Semester, UUID> {
    
    List<Semester> findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrDeletedAtIsNullAndNameContainingIgnoreCaseOrDeletedAtIsNullAndSchoolYearNameContainingIgnoreCaseOrderByCreatedAtDesc(String codeKeyword, String nameKeyword, String schoolYearNameKeyword);
    
    List<Semester> findByDeletedAtIsNullOrderByCreatedAtDesc();

    List<Semester> findByDeletedAtIsNullAndIsActiveTrueOrderByStartDateDesc();
}
