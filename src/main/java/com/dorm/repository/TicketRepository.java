package com.dorm.repository;

import com.dorm.model.Checkout;
import com.dorm.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByStudentId(UUID studentId);
}