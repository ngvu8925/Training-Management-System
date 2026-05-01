package com.example.demo.n5.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n5.model.entity.StudentCourseSection;

public interface StudentCourseSectionRepository extends JpaRepository<StudentCourseSection, UUID> {
    
    List<StudentCourseSection> findByDeletedAtIsNullOrderByCreatedAtDesc();
    
    List<StudentCourseSection> findByStudentIdAndDeletedAtIsNull(UUID studentId);
    
    List<StudentCourseSection> findByCourseSectionIdAndDeletedAtIsNull(UUID courseSectionId);

    boolean existsByStudentIdAndCourseSectionIdAndDeletedAtIsNull(UUID studentId, UUID courseSectionId);

    long countByCourseSectionIdAndDeletedAtIsNullAndIsActiveTrue(UUID courseSectionId);
}
