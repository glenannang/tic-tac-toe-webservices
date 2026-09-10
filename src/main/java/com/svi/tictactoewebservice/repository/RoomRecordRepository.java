package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.Room;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface RoomRecordRepository {

    void addGameToRoom(String roomCode, UUID gameId) throws IOException;

    Room findRoom(String roomCode) throws IOException;

    List<Room> findAllRooms() throws IOException;
}