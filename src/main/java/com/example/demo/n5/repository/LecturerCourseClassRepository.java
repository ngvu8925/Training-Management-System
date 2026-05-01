package com.example.demo.n5.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.n5.model.entity.LecturerCourseClass;

public interface LecturerCourseClassRepository extends JpaRepository<LecturerCourseClass, UUID> {

    List<LecturerCourseClass> findByDeletedAtIsNullOrderByCreatedAtDesc();

    List<LecturerCourseClass> findByCourseClassIdAndDeletedAtIsNull(UUID courseClassId);

    List<LecturerCourseClass> findByEmployeeIdAndDeletedAtIsNull(UUID employeeId);

    boolean existsByEmployeeIdAndCourseClassIdAndDeletedAtIsNull(UUID employeeId, UUID courseClassId);

    @Query("""
        select lcc
        from LecturerCourseClass lcc, CourseSection cs
        where lcc.deletedAt is null
          and cs.deletedAt is null
          and cs.id = lcc.courseClassId
          and lcc.employeeId = :employeeId
          and cs.semesterId = :semesterId
        order by cs.createdAt desc
        """)
    List<LecturerCourseClass> findSchedule(@Param("employeeId") UUID employeeId, @Param("semesterId") UUID semesterId);
}
