package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.dto.response.GameIdResponse;
import com.svi.tictactoewebservice.dto.response.GameDetailsResponse;
import com.svi.tictactoewebservice.dto.response.ApiResponse;

import java.io.IOException;


public interface GameService {

    ApiResponse saveMove(MoveRequest request) throws IOException;

    GameDetailsResponse getGameDetails(String gameId) throws IOException;

    GameIdResponse createGameRecord(String roomCode) throws IOException;
}