package com.example.country.service;

import com.example.country.exception.RoomNotFoundException;
import com.example.country.models.Room;
import com.example.country.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;


    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room get(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
}

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        roomRepository.deleteById(id);
    }

    public void updateStatus(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        room.setStatus(!room.isStatus());
        roomRepository.save(room);

    }

    public List<Room> createAll(List<Room> rooms) {
        return roomRepository.saveAll(rooms);
    }




}
