package com.dorm.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    private UUID id;

    private UUID studentId; // instead of @ManyToOne Student

    private String preferredRoomType;
    private String studentNumber;
    private String roomNumber;
    private String name;
    private String email;
    private String gender;
    private String nationality;
    private String major;
    private String emergencyContact;
    private Timestamp submittedAt;

    private String status; // "pending", "accepted", "rejected"
}