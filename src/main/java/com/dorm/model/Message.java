package com.dorm.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Message {

    @Id
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Student> readBy = new ArrayList<>();

    private Timestamp createdAt;
}
