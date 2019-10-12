package com.domy.zoomanagement.controllers;

import com.domy.zoomanagement.models.Room;
import com.domy.zoomanagement.repository.AnimalsRepository;
import com.domy.zoomanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    AnimalsRepository animalsRepository;

    @Autowired
    RoomRepository roomRepository;

    @GetMapping(value = "/rooms", produces = {"application/json"})
    public @ResponseBody
    List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @PostMapping(value = "/rooms", consumes = "application/json")
    public Room createRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @PutMapping("/room/{roomId}")
    public Room updateRoom(@PathVariable Long roomId,
                               @Valid @RequestBody Room request) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    if(request.getName() != null) room.setName(request.getName());
                    return roomRepository.save(room);
                }).orElseThrow(() -> new ResourceNotFoundException(("Room not found with given ID")));
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    roomRepository.delete(room);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Room not found with given ID"));
    }

}
