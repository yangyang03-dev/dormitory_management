package com.dorm.service;

import com.dorm.model.Application;
import com.dorm.repository.ApplicationRepository;
import com.dorm.model.Room;
import com.dorm.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository repo;
    private final StudentService studentService;
    private final ContractService contractService;

    public List<Application> getAll() {
        return repo.findAll();
    }

    public Application create(Application app) {
        app.setId(UUID.randomUUID());
        app.setRoomNumber(null);
        app.setSubmittedAt(new Timestamp(System.currentTimeMillis()));
        app.setStatus("pending");
        return repo.save(app);
    }
    public void approveApplication(UUID appId, String roomNumber) {
        Application app = repo.findById(appId).orElseThrow();

        if (!"pending".equalsIgnoreCase(app.getStatus())) {
            throw new IllegalStateException("Only pending applications can be approved.");
        }

        // Step 1: update the application status
        app.setStatus("accepted");
        repo.save(app);

        // Step 2: create student
        app.setRoomNumber(roomNumber);
        UUID studentId = studentService.createStudentFromApplication(app);
        app.setStudentId(studentId);
        // Step 3: create contract with room number
        contractService.createContract(studentId, roomNumber);


    }
    public void rejectApplication(UUID appId) {
        Application app = repo.findById(appId).orElseThrow();
        app.setStatus("rejected");
        repo.save(app);
    }
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Application with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }
}