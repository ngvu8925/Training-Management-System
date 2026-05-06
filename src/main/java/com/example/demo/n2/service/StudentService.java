package com.example.demo.n2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.n2.dto.StudentRequest;
import com.example.demo.n2.dto.StudentResponse;
import com.example.demo.n2.model.entity.Student;
import com.example.demo.n2.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<StudentResponse> getAll() {
        return repo.findByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public StudentResponse getById(UUID id) {
        return toResponse(getStudentEntity(id));
    }

    @Transactional
    public StudentResponse create(StudentRequest request) {
        validateCodeForCreate(request.getCode());

        Student student = new Student();
        mapRequestToEntity(student, request);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setDeletedAt(null);
        student.setDeletedBy(null);
        student.setIsActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE);
        student.setStatus(hasText(request.getStatus()) ? request.getStatus().trim() : "studying");

        return toResponse(repo.save(student));
    }

    @Transactional
    public StudentResponse update(UUID id, StudentRequest request) {
        Student existing = getStudentEntity(id);
        validateCodeForUpdate(request.getCode(), id);

        mapRequestToEntity(existing, request);
        existing.setUpdatedAt(LocalDateTime.now());

        return toResponse(repo.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        Student student = getStudentEntity(id);
        student.setDeletedAt(LocalDateTime.now());
        student.setIsActive(Boolean.FALSE);
        repo.save(student);
    }

    public List<StudentResponse> search(String fullname) {
        return repo.findByDeletedAtIsNullAndFullnameContainingIgnoreCaseOrderByCreatedAtDesc(fullname)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private Student getStudentEntity(UUID id) {
        return repo.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay sinh vien"));
    }

    private void validateCodeForCreate(String code) {
        if (repo.existsByCodeIgnoreCaseAndDeletedAtIsNull(code.trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ma sinh vien da ton tai");
        }
    }

    private void validateCodeForUpdate(String code, UUID id) {
        if (repo.existsByCodeIgnoreCaseAndIdNotAndDeletedAtIsNull(code.trim(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ma sinh vien da ton tai");
        }
    }

    private void mapRequestToEntity(Student student, StudentRequest request) {
        student.setUser_id(request.getUserId());
        student.setCode(request.getCode().trim());
        student.setFullname(request.getFullname().trim());
        student.setDate_of_birth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setPersonal_identification_number(request.getPersonalIdentificationNumber());
        student.setDate_of_issue(request.getDateOfIssue());
        student.setCard_place(request.getCardPlace());
        student.setAddress(request.getAddress());
        student.setCurrent_address(request.getCurrentAddress());
        student.setAcademic_year_year(request.getAcademicYearYear());
        student.setDepartment_id(request.getDepartmentId());
        student.setMajor_id(request.getMajorId());
        student.setTraining_program_id(request.getTrainingProgramId());
        student.setStatus(request.getStatus());
        student.setStudent_classe_id(request.getStudentClasseId());
        student.setAdmission_year(request.getAdmissionYear());
        student.setCreatedBy(request.getCreatedBy());
        student.setUpdatedBy(request.getUpdatedBy());
        if (request.getIsActive() != null) {
            student.setIsActive(request.getIsActive());
        }
    }

    private StudentResponse toResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setUserId(student.getUser_id());
        response.setCode(student.getCode());
        response.setFullname(student.getFullname());
        response.setDateOfBirth(student.getDate_of_birth());
        response.setGender(student.getGender());
        response.setPersonalIdentificationNumber(student.getPersonal_identification_number());
        response.setDateOfIssue(student.getDate_of_issue());
        response.setCardPlace(student.getCard_place());
        response.setAddress(student.getAddress());
        response.setCurrentAddress(student.getCurrent_address());
        response.setAcademicYearYear(student.getAcademic_year_year());
        response.setDepartmentId(student.getDepartment_id());
        response.setMajorId(student.getMajor_id());
        response.setTrainingProgramId(student.getTraining_program_id());
        response.setStatus(student.getStatus());
        response.setStudentClasseId(student.getStudent_classe_id());
        response.setAdmissionYear(student.getAdmission_year());
        response.setCreatedAt(student.getCreatedAt());
        response.setUpdatedAt(student.getUpdatedAt());
        response.setIsActive(student.getIsActive());
        return response;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
