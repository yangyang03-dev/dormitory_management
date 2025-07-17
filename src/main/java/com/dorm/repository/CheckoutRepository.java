package com.dorm.repository;
import com.dorm.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CheckoutRepository  extends JpaRepository<Checkout, UUID>{
    Optional<Checkout> findByStudentUUID(UUID studentId);
}
