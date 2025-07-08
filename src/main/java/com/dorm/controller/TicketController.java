package com.dorm.controller;

import com.dorm.model.Room;
import com.dorm.model.Student;
import com.dorm.model.Ticket;
import com.dorm.repository.RoomRepository;
import com.dorm.repository.StudentRepository;
import com.dorm.service.TicketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final StudentRepository studentRepo;
    private final RoomRepository roomRepo;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public TicketController(TicketService ticketService, StudentRepository studentRepo, RoomRepository roomRepo) {
        this.ticketService = ticketService;
        this.studentRepo = studentRepo;
        this.roomRepo = roomRepo;
    }

    @PostMapping("/upload")
    public ResponseEntity<Ticket> createTicketWithImage(
            @RequestParam UUID studentId,
            @RequestParam UUID roomId,
            @RequestParam String description,
            @RequestParam MultipartFile file
    ) throws IOException {

        Student student = studentRepo.findById(studentId).orElseThrow();
        Room room = roomRepo.findById(roomId).orElseThrow();

        String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        File dest = new File(uploadDir, filename);
        file.transferTo(dest);

        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setStudent(student);
        ticket.setRoom(room);
        ticket.setDescription(description);
        ticket.setImageUrl("/uploads/" + filename);
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setStatus("submitted");

        return new ResponseEntity<>(ticketService.create(ticket), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAll();
    }
}