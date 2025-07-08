package com.dorm.controller;

import com.dorm.model.Room;
import org.springframework.web.bind.annotation.*;
import com.dorm.service.RoomService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return service.getAll();
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return service.create(room);
    }
}