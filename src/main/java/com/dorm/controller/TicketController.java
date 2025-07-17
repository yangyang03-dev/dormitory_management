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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Ticket> createTicketWithImage(
            @RequestParam UUID studentId,
            @RequestParam String roomNumber,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile file
    ) throws IOException {

        Student student = studentRepo.findById(studentId).orElseThrow();
        Room room = roomRepo.findById(roomNumber).orElseThrow();
        Ticket ticket = new Ticket();
        if(file != null ) {
            String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            String uploadPath = System.getProperty("user.dir") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File dest = new File(uploadDir, filename);
            file.transferTo(dest);
            ticket.setImageUrl("/uploads/" + filename);
        }
        ticket.setStudentId(student.getId());
        ticket.setRoomNumber(room.getRoomNumber());
        ticket.setId(UUID.randomUUID());
        ticket.setDescription(description);
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setStatus("submitted");

        return new ResponseEntity<>(ticketService.create(ticket), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable UUID id) {
        return ticketService.getById(id);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Ticket> getAllTickets() {
        return ticketService.getAll();
    }
    @GetMapping("/me")
    public List<Ticket> getMyTicket(Authentication authentication) {
        String email=authentication.getName();
        List<Ticket> ticketOpt=ticketService.getByStudentEmail(email);
        return ticketOpt.isEmpty()?null:ticketOpt;
    }
    @PutMapping("/{id}/solve")
    @PreAuthorize("hasRole('ADMIN')")
    public Ticket solveTicket(@PathVariable UUID id) {
        return ticketService.updateStatus(id, "solved");
    }
    @DeleteMapping ("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTicket(@PathVariable UUID id) {
        ticketService.delete(id);
    }
}