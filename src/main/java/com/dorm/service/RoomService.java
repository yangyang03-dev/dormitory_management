package com.dorm.service;

import com.dorm.model.Room;
import com.dorm.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Room create(Room room) {
        room.setId(UUID.randomUUID());
        room.setAvailable(true);
        return repo.save(room);
    }
}