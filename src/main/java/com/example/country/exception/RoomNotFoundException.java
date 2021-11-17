package com.example.country.exception;

public class RoomNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 9142853073209589915L;

    public RoomNotFoundException(Long id) {
        super("Could not find room with id " + id + ".");
    }
}
