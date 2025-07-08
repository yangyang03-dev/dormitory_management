package com.dorm.service;

import com.dorm.model.Ticket;
import com.dorm.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository repo;

    public TicketService(TicketRepository repo) {
        this.repo = repo;
    }

    public List<Ticket> getAll() {
        return repo.findAll();
    }

    public Ticket create(Ticket ticket) {
        ticket.setId(UUID.randomUUID());
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setStatus("submitted");
        return repo.save(ticket);
    }

    public Ticket updateStatus(UUID id, String status) {
        Ticket ticket = repo.findById(id).orElseThrow();
        ticket.setStatus(status);
        return repo.save(ticket);
    }
}