package com.dorm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private UUID id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private boolean readByMe;

}
