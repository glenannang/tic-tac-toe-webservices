package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.dto.response.RoomListResponse;

import java.io.IOException;


public interface PlayerService {
    GameListResponse getPlayerGames(String playerId) throws IOException;
    RoomListResponse getPlayerRooms(String playerId) throws IOException;
}