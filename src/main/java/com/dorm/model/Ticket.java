package com.dorm.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    private UUID id;

    private UUID studentId;

    private String roomNumber;

    private String description;

    private String imageUrl;

    private String status;

    private Timestamp createdAt;
}