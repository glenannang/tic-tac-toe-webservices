package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.dto.response.GameIdResponse;
import com.svi.tictactoewebservice.dto.response.GameDetailsResponse;
import com.svi.tictactoewebservice.dto.response.ApiResponse;

import java.io.IOException;
import java.util.UUID;


public interface GameService {

    ApiResponse saveMove(MoveRequest request)throws IOException;

    GameDetailsResponse getGameDetails(UUID gameId) throws IOException;

    GameIdResponse createGameRecord(String roomCode) throws IOException;
}