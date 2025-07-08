package com.dorm.service;

import com.dorm.model.Application;
import com.dorm.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    private final ApplicationRepository repo;

    public ApplicationService(ApplicationRepository repo) {
        this.repo = repo;
    }

    public List<Application> getAll() {
        return repo.findAll();
    }

    public Application create(Application app) {
        app.setId(UUID.randomUUID());
        app.setSubmittedAt(new Timestamp(System.currentTimeMillis()));
        app.setStatus("pending");
        return repo.save(app);
    }

    public Application updateStatus(UUID id, String status) {
        Application app = repo.findById(id).orElseThrow();
        app.setStatus(status);
        return repo.save(app);
    }
}