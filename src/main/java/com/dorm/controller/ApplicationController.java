package com.dorm.controller;

import com.dorm.model.Application;
import com.dorm.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    public List<Application> getAllApplications() {
        return service.getAll();
    }

    @PostMapping
    public Application createApplication(@RequestBody Application app) {
        return service.create(app);
    }

    @PutMapping("/{id}/status")
    public Application updateApplicationStatus(@PathVariable UUID id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}