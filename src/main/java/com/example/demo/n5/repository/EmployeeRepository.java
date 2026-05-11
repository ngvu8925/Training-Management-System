package com.example.demo.n5.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.n5.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
