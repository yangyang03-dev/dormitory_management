package com.dorm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private String roomNumber;
    @Column(unique = true)
    private UUID id;
    private int capacity;

    private String type; // e.g., "single", "double", "quiet_floor"

    private boolean isAvailable;
}