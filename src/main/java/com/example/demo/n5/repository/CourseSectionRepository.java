package com.example.demo.n5.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.n5.model.entity.CourseSection;

public interface CourseSectionRepository extends JpaRepository<CourseSection, UUID> {
    
    List<CourseSection> findByDeletedAtIsNullAndCodeContainingIgnoreCaseOrderByCreatedAtDesc(String codeKeyword);
    
    List<CourseSection> findByDeletedAtIsNullOrderByCreatedAtDesc();

    Optional<CourseSection> findByIdAndDeletedAtIsNull(UUID id);

    @Query("""
        select cs
        from CourseSection cs
        where cs.deletedAt is null
          and (:semesterId is null or cs.semesterId = :semesterId)
          and (:courseId is null or cs.courseId = :courseId)
          and (:status is null or lower(cs.status) = lower(:status))
        order by cs.createdAt desc
        """)
    List<CourseSection> filter(@Param("semesterId") UUID semesterId, @Param("courseId") UUID courseId, @Param("status") String status);
}
