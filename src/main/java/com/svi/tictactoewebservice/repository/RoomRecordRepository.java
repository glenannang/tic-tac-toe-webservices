package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.Room;

import java.io.IOException;
import java.util.List;

public interface RoomRecordRepository {

    void addGameToRoom(String roomCode, String gameId) throws IOException;

    Room findRoom(String roomCode) throws IOException;

    List<Room> findAllRooms() throws IOException;
}