package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.dto.response.RoomResponse;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRepository;

import java.io.IOException;


public class RoomService {

    private final RoomRepository roomRepository = new RoomRepository();

    public RoomResponse createRoom(RoomRequest request) throws IOException {

        Room room = new Room();
        room.setRoomCode(request.getRoomCode());
        roomRepository.createRoom(room);

        return new RoomResponse(room.getRoomCode(),room.getGameIds());
    }

    public RoomResponse getRoom(String roomCode) throws IOException {
        Room room = roomRepository.findRoom(roomCode);

        if (room == null){
            return null;
        }

        return new RoomResponse(room.getRoomCode(),room.getGameIds());
    }

    public void addGameToRoom(String roomCode, String gameId) throws IOException {

        roomRepository.addGameToRoom(roomCode, gameId);
    }
}