package com.dorm.service;

import com.dorm.model.Room;
import com.dorm.model.Student;
import com.dorm.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository repo;

    public RoomService(RoomRepository repo) {
        this.repo = repo;
    }

    public List<Room> getAll() {
        return repo.findAll();
    }
    public Room getById(String roomNumber) {
        return repo.getById(roomNumber);
    }
    public Room create(Room room) {
        room.setId(UUID.randomUUID());
        room.setAvailable(true);
        return repo.save(room);
    }
    public void  updateToMoveIn(String roomNumber) {
        Room room=repo.findById(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        room.setAvailable(!room.isAvailable());
        repo.save(room);
    }
    public void delete(Room room) {
        repo.delete(room);
    }
}