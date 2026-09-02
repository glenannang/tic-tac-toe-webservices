package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRepository;

import java.io.IOException;


public class RoomService {

    private final RoomRepository roomRepository = new RoomRepository();

    public Room createRoom(RoomRequest request) throws IOException {

        Room room = new Room();
        room.setRoomCode(request.getRoomCode());
        roomRepository.createRoom(room);

        return room;
    }

    public Room getRoom(String roomCode) throws IOException {
        return roomRepository.findRoom(roomCode);
    }

    public void addGameToRoom(String roomCode, String gameId) throws IOException {

        roomRepository.addGameToRoom(roomCode, gameId);
    }
}