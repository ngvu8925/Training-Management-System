package com.example.demo.n5.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n5.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findByDeletedAtIsNullAndIsActiveTrueOrderByCodeAsc();
}
