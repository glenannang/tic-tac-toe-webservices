package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.dto.response.RoomResponse;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRepository;
import com.svi.tictactoewebservice.service.RoomService;

import java.io.IOException;


public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository = new RoomRepository();

    @Override
    public RoomResponse createRoom(RoomRequest request) throws IOException {

        Room room = new Room();
        room.setRoomCode(request.getRoomCode());
        roomRepository.createRoom(room);

        return new RoomResponse(room.getRoomCode(),room.getGameIds());
    }

    @Override
    public RoomResponse getRoom(String roomCode) throws IOException {
        Room room = roomRepository.findRoom(roomCode);

        if (room == null){
            return null;
        }

        return new RoomResponse(room.getRoomCode(),room.getGameIds());
    }
    @Override
    public void addGameToRoom(String roomCode, String gameId) throws IOException {

        roomRepository.addGameToRoom(roomCode, gameId);
    }
}