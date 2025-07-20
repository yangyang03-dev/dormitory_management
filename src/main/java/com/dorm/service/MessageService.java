package com.dorm.service;

import com.dorm.dto.MessageDTO;
import com.dorm.model.Message;
import com.dorm.model.Student;
import com.dorm.model.Ticket;
import com.dorm.repository.MessageRepository;
import com.dorm.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepo;
    private final StudentRepository studentRepo;
    private final StudentService studentService;



    public MessageService(MessageRepository messageRepo, StudentRepository studentRepo, StudentService studentService) {
        this.messageRepo = messageRepo;
        this.studentRepo = studentRepo;
        this.studentService = studentService;
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Optional<Message> getMessageById(UUID id) {
        return messageRepo.findById(id);
    }
    public List<MessageDTO> getAllForStudent(String email) {
        Student student = studentService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        UUID studentId = student.getId();

        return messageRepo.findAll().stream()
                .map(message -> {
                    boolean readByMe = message.getReadBy().stream()
                            .anyMatch(s -> s.getId().equals(studentId));
                    return new MessageDTO(
                            message.getId(),
                            message.getTitle(),
                            message.getContent(),
                            message.getCreatedAt(),
                            readByMe
                    );
                })
                .toList();
    }
    public Message createMessage(Message message) {
        message.setId(UUID.randomUUID());
        message.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        message.setReadBy(new ArrayList<>());
        return messageRepo.save(message);
    }

    public Message markAsRead(UUID messageId, UUID studentId) {
        Message message = messageRepo.findById(messageId).orElseThrow();
        Student student = studentRepo.findById(studentId).orElseThrow();

        if (!message.getReadBy().contains(student)) {
            message.getReadBy().add(student);
            messageRepo.save(message);
        }
        return message;
    }

    public void deleteMessage(UUID messageId) {
        messageRepo.deleteById(messageId);
    }

    public Message updateMessage(UUID messageId, String newTitle, String newContent) {
        Message message = messageRepo.findById(messageId).orElseThrow();
        message.setTitle(newTitle);
        message.setContent(newContent);
        return messageRepo.save(message);
    }
}