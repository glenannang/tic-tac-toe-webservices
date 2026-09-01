package com.svi.tictactoewebservice.service;
import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.repository.GameRepository;

import java.io.IOException;
import java.util.List;

public class GameService {
    private final GameRepository gameRepository = new GameRepository();

    public void saveMove(MoveRecord record) throws IOException {
        gameRepository.saveMove(record);
    }

    public List<String> getPlayerGames(String playerId) throws IOException {
        return gameRepository.findGamesByPlayerId(playerId);
    }

}
