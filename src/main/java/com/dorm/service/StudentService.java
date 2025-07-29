package com.dorm.service;

import com.dorm.model.Application;
import com.dorm.model.Student;
import com.dorm.repository.StudentRepository;
import org.springframework.stereotype.Service;
import com.dorm.service.ApplicationService;
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

    public Student getById(UUID id) {
        return repo.getById(id);
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
    public Student update(UUID id, Student student) {
        Student existing = repo.findById(id).orElseThrow();
        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setGender(student.getGender());
        existing.setNationality(student.getNationality());
        existing.setMajor(student.getMajor());
        existing.setEmergencyContact(student.getEmergencyContact());
        return repo.save(existing);
    }
    public Optional<Student> getByEmail(String email) {
        return repo.findByEmail(email);
    }
    public UUID createStudentFromApplication(Application app) {// assuming partial object
        Student student = new Student();
        student.setId(UUID.randomUUID());
        student.setStudentNumber(app.getStudentNumber());
        student.setRoomNumber(app.getRoomNumber());
        student.setName(app.getName());
        student.setEmail(app.getEmail());
        student.setGender(app.getGender());
        student.setNationality(app.getNationality());
        student.setMajor(app.getMajor());
        student.setEmergencyContact(app.getEmergencyContact());
        student.setStatus("accepted");

        repo.save(student);
        return student.getId();
    }
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Student with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }
}