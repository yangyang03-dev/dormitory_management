package com.dorm.service;

import com.dorm.model.Student;
import com.dorm.model.Ticket;
import com.dorm.repository.TicketRepository;
import com.dorm.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository repo;
    private final StudentRepository studentRepository;

    public TicketService(TicketRepository repo, StudentRepository studentRepository) {
        this.repo = repo;
        this.studentRepository = studentRepository;
    }

    public List<Ticket> getAll() {
        return repo.findAll();
    }
    public Ticket getById(UUID id) {
        return repo.getById(id);
    }
    public Ticket create(Ticket ticket) {
        ticket.setId(UUID.randomUUID());
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setStatus("submitted");
        return repo.save(ticket);
    }
    public List<Ticket> getByStudentEmail(String email) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
       return studentOpt.map(student -> repo.findByStudentId(student.getId())).orElse(Collections.emptyList());

    }
    public Ticket updateStatus(UUID id, String status) {
        Ticket ticket = repo.findById(id).orElseThrow();
        ticket.setStatus(status);
        return repo.save(ticket);
    }
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Ticket with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }
}