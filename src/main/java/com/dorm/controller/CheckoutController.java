package com.dorm.controller;

import com.dorm.model.Checkout;
import com.dorm.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkouts")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Checkout> getAll() {
        return service.getAll();
    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Checkout> getMyCheckout(Authentication authentication) {
        String email = authentication.getName(); // current logged-in user
        Optional<Checkout> checkoutOpt = service.getByStudentEmail(email);

        return checkoutOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping
    public Checkout create(@RequestBody Checkout checkout) {
        return service.create(checkout);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Checkout updateStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
