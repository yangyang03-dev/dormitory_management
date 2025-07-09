package com.dorm.controller;

import com.dorm.model.Application;
import com.dorm.service.ApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Application> getAllApplications() {
        return service.getAll();
    }

    @PostMapping
    public Application createApplication(@RequestBody Application app) {
        return service.create(app);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Application updateApplicationStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}