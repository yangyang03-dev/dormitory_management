package com.dorm.security;

// A simple DTO (Data Transfer Object) for the login request body
public record AuthRequest(String username, String password) {
}
