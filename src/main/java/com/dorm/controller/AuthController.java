package com.dorm.controller;

import com.dorm.security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        // TEMP: Hardcoded check — replace with DB check later
        if ("admin@dorm.com".equals(request.getEmail()) && "123456".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail());
            return Map.of("token", token);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}