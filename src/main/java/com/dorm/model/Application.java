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

    @ManyToOne
    private Student student;

    private String preferredRoomType;

    private Timestamp submittedAt;

    private String status; // "pending", "accepted", "rejected"
}