package com.dorm.service;

import com.dorm.model.Student;
import com.dorm.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Optional<Student> getById(UUID id) {
        return repo.findById(id);
    }

    public Student create(Student student) {
        student.setId(UUID.randomUUID());
        return repo.save(student);
    }

    public Student updateStatus(UUID id, String status) {
        Student student = repo.findById(id).orElseThrow();
        student.setStatus(status);
        return repo.save(student);
    }
}