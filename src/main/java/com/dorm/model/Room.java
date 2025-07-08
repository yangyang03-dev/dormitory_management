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
    private UUID id;

    @Column(unique = true)
    private String roomNumber;

    private int capacity;

    private String type; // e.g., "single", "double", "quiet_floor"

    private boolean isAvailable;
}