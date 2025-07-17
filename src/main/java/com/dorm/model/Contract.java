package com.dorm.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    private UUID id;

    private UUID studentId;

    private UUID roomId;

    private String roomNumber;

    private String contractUrl;

    private Timestamp signedAt;
}