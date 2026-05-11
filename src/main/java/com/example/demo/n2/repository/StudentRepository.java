package com.example.demo.n2.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.n2.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByDeletedAtIsNullOrderByCreatedAtDesc();

    List<Student> findByDeletedAtIsNullAndFullnameContainingIgnoreCaseOrderByCreatedAtDesc(String fullname);

    Optional<Student> findByIdAndDeletedAtIsNull(UUID id);

    boolean existsByCodeIgnoreCaseAndDeletedAtIsNull(String code);

    boolean existsByCodeIgnoreCaseAndIdNotAndDeletedAtIsNull(String code, UUID id);
}
