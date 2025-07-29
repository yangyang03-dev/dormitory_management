package com.dorm.controller;

import com.dorm.model.Student;
import com.dorm.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
//    @PreAuthorize("hasRole('ADMIN')")
    public List<Student> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public Student getById(@PathVariable UUID id) {
        return service.getById(id);
    }
    @GetMapping("/me")
    public Student getCurrentStudent(Authentication authentication) {
        String email = authentication.getName(); // Email is the username
        return service.getByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }
    @PostMapping
    public Student create(@RequestBody Student student) {
        return service.create(student);
    }

    @PutMapping("/{id}/status")
    public Student updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
    @PutMapping("/{id}")
    public Student update(@PathVariable UUID id, @RequestBody Student student) {
        return service.update(id, student);
    }
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}