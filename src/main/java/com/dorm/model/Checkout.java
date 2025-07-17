package com.dorm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {



        @Id
        private UUID id;

        private UUID studentUUID;

        private String reason; // e.g., "graduated", "moved out", "expelled"

        private Timestamp requestedAt;

        private Date preferredDate;
        private String status; // "pending", "approved", "rejected"
}
