package com.svi.tictactoewebservice.service;


import com.svi.tictactoewebservice.dto.response.RoomResponse;

import java.io.IOException;
import java.util.UUID;

public interface RoomService {

    RoomResponse getRoom(String roomCode) throws IOException;

    void addGameToRoom(String roomCode, UUID gameId) throws IOException;
}