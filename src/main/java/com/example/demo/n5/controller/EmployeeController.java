package com.example.demo.n5.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.n5.model.entity.Employee;
import com.example.demo.n5.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }
}
