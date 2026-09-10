package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.dto.response.RoomResponse;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRecordRepository;
import com.svi.tictactoewebservice.repository.impl.RoomRepository;
import com.svi.tictactoewebservice.service.RoomService;
import com.svi.tictactoewebservice.repository.impl.CassandraRepository;

import java.io.IOException;


public class RoomServiceImpl implements RoomService {

    private final RoomRecordRepository roomRepository = new CassandraRepository(CassandraConnection.getInstance().getSession());


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