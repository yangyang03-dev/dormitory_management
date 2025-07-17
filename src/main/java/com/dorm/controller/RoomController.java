package com.dorm.controller;

import com.dorm.model.Room;
import com.dorm.model.Student;
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
    @GetMapping("/{roomNumber}")
    public Room getById(@PathVariable String roomNumber) {
        return service.getById(roomNumber);
    }
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return service.create(room);
    }
    @PutMapping
    public void updateToMoveIn(@RequestParam String roomNumber){
         service.updateToMoveIn(roomNumber);
    }
    @DeleteMapping("/{roomNumber}")
    public void deleteRoom(@PathVariable String roomNumber) {
        service.delete(service.getById(roomNumber));
    }
}