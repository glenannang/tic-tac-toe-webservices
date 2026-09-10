package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.dto.response.RoomResponse;
import com.svi.tictactoewebservice.exception.InternalServerException;
import com.svi.tictactoewebservice.exception.NotFoundException;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRecordRepository;
import com.svi.tictactoewebservice.repository.impl.RoomRepository;
import com.svi.tictactoewebservice.service.RoomService;
import com.svi.tictactoewebservice.repository.impl.CassandraRepository;

import java.io.IOException;
import java.util.UUID;


public class RoomServiceImpl implements RoomService {

    private final RoomRecordRepository roomRepository = new RoomRepository();
    //new CassandraRepository(CassandraConnection.getInstance().getSession());


    @Override
    public RoomResponse getRoom(String roomCode){

        try {
            Room room = roomRepository.findRoom(roomCode);

            if (room == null) {
                throw new NotFoundException("Room not found.");
            }

            return new RoomResponse(room.getRoomCode(), room.getGameIds());

        } catch (IOException e) {
            throw new InternalServerException("The server ran into an unexpected exception.", e);
        }
    }

    @Override
    public void addGameToRoom(String roomCode, UUID gameId){
        try {
            roomRepository.addGameToRoom(roomCode, gameId);
        } catch (IOException e) {
            throw new InternalServerException("The server ran into an unexpected exception.", e);
        }
    }
}