package com.dorm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String gender;
    private String nationality;
    private String major;
    private String emergencyContact;
    private String status;
}