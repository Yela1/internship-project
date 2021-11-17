package com.example.country.controller;

import com.example.country.models.Room;
import com.example.country.service.CheckIPService;
import com.example.country.service.GetIPService;
import com.example.country.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;


@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final CheckIPService checkIPService;

    private final GetIPService getIPService;

    @Value("${check.ip}")
    private String uri;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable("id") Long id) throws IOException {
        Room room = roomService.get(id);
        InetAddress ipAddress = getIPService.getIp(uri);
        if(!checkIPService.checkIp(room,ipAddress)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }


    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(room));
    }

    @PostMapping("/all")
    public ResponseEntity<List<Room>> createRooms(@RequestBody List<Room> rooms){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createAll(rooms));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") Long id){
        roomService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Element with id - " + id + " was deleted" );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Long id) {
        roomService.updateStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(roomService.get(id));
    }
}
