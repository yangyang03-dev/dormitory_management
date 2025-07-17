package com.dorm.service;

import com.dorm.model.Checkout;
import com.dorm.model.Student;
import com.dorm.repository.CheckoutRepository;
import com.dorm.repository.StudentRepository;
import com.dorm.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutRepository repo;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    public List<Checkout> getAll() {
        return repo.findAll();
    }
    public Optional<Checkout> getByStudentEmail(String email) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isEmpty()) {
            return Optional.empty();
        }

        return repo.findByStudentUUID(studentOpt.get().getId());
    }
    public Checkout create(Checkout checkout) {
        checkout.setId(UUID.randomUUID());
        checkout.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        checkout.setStatus("pending");
        return repo.save(checkout);
    }

    public Checkout updateStatus(UUID id, String status) {
        Checkout checkout = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Checkout with ID " + id + " not found"));

        String oldStatus = checkout.getStatus();
        checkout.setStatus(status);
        Checkout updated = repo.save(checkout);

        // If status changes to accepted → update student status
        if ("pending".equalsIgnoreCase(oldStatus) && "accepted".equalsIgnoreCase(status)) {
            UUID studentId = checkout.getStudentUUID();
            studentService.updateStatus(studentId, "checkout_approved");
        }

        return updated;
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Checkout with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }
}