package com.dorm.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        // For now: hardcoded credentials (replace with DB check later)
        if ("admin@dorm.com".equals(request.getEmail()) && "123456".equals(request.getPassword())) {
            return Map.of("token", "mock-token-abc123");
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}