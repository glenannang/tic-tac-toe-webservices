package com.svi.tictactoewebservice.service;


import com.svi.tictactoewebservice.dto.response.RoomResponse;

import java.io.IOException;

public interface RoomService {

    RoomResponse getRoom(String roomCode) throws IOException;

    void addGameToRoom(String roomCode, String gameId) throws IOException;
}