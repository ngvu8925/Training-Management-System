package com.example.demo.n5.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.n5.model.entity.Employee;
import com.example.demo.n5.repository.EmployeeRepository;
import com.example.demo.n5.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeServiceImpl(EmployeeRepository repository) { this.repository = repository; }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }
}
