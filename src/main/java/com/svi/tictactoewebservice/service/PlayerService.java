package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.dto.response.RoomListResponse;

import java.io.IOException;
import java.util.UUID;


public interface PlayerService {
    GameListResponse getPlayerGames(UUID playerId) ;
    RoomListResponse getPlayerRooms(UUID playerId) ;
}