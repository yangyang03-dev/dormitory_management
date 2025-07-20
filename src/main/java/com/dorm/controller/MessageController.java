package com.dorm.controller;

import com.dorm.dto.MessageDTO;
import com.dorm.model.Message;
import com.dorm.model.Student;
import com.dorm.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Message m : messages) {
            response.add(stripReadBy(m));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMessage(@PathVariable UUID id) {
        return messageService.getMessageById(id)
                .map(message -> ResponseEntity.ok(stripReadBy(message)))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/readers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReaders(@PathVariable UUID id) {
        Optional<Message> optionalMessage = messageService.getMessageById(id);

        if (optionalMessage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        }

        Message message = optionalMessage.get();
        List<Map<String, Object>> readers = message.getReadBy().stream().map(student -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", student.getId());
            map.put("name", student.getName());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(readers);
    }
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMessage(@RequestBody Message message) {
        Message saved = messageService.createMessage(message);
        return ResponseEntity.ok(stripReadBy(saved));
    }
    @GetMapping("/student")
    public ResponseEntity<List<MessageDTO>> getMessagesForStudent(@AuthenticationPrincipal UserDetails user) {
        String email = user.getUsername(); // comes from JWT
        List<MessageDTO> messages = messageService.getAllForStudent(email);
        return ResponseEntity.ok(messages);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMessage(@PathVariable UUID id, @RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String content = payload.get("content");
        Message updated = messageService.updateMessage(id, title, content);
        return ResponseEntity.ok(stripReadBy(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id, @RequestParam UUID studentId) {
        messageService.markAsRead(id, studentId);
        return ResponseEntity.ok().build();
    }

    // Helper method to exclude readBy list
    private Map<String, Object> stripReadBy(Message m) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", m.getId());
        map.put("title", m.getTitle());
        map.put("content", m.getContent());
        map.put("createdAt", m.getCreatedAt());
        return map;
    }
}