package com.dorm.controller;

import com.dorm.model.Student;
import com.dorm.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return service.create(student);
    }

    @PutMapping("/{id}/status")
    public Student updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}