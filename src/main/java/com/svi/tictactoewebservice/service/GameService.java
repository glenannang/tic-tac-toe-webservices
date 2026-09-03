package com.svi.tictactoewebservice.service;

import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.dto.response.GameIdResponse;
import com.svi.tictactoewebservice.model.MoveRecord;

import java.io.IOException;
import java.util.List;

public interface GameService {

    void saveMove(MoveRequest request) throws IOException;

    List<String> getPlayerGames(String playerId) throws IOException;

    List<MoveRecord> getGameDetails(String gameId) throws IOException;

    GameIdResponse createGameRecord(String roomCode) throws IOException;
}