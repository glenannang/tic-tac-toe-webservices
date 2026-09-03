package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.dto.response.RoomResponse;

import java.io.IOException;

public interface RoomService {

    RoomResponse createRoom(RoomRequest request) throws IOException;

    RoomResponse getRoom(String roomCode) throws IOException;

    void addGameToRoom(String roomCode, String gameId) throws IOException;
}