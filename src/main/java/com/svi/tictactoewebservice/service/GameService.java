package com.svi.tictactoewebservice.service;
import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.repository.GameRepository;

import java.io.IOException;
import java.util.List;

public class GameService {
    private final GameRepository gameRepository = new GameRepository();

    public void saveMove(MoveRequest request) throws IOException {
        MoveRecord record = new MoveRecord();
        record.setGameid(request.getGameid());
        record.setPlayerid(request.getPlayerid());
        record.setSymbol(request.getSymbol());
        record.setLocation(request.getLocation());
        record.setDatesave(request.getDatesave());

        gameRepository.saveMove(record);
    }

    public List<String> getPlayerGames(String playerId) throws IOException {
        return gameRepository.findGamesByPlayerId(playerId);
    }

    public List<MoveRecord> getGameDetails(String gameId) throws IOException {
        return gameRepository.findMovesByGameId(gameId);
    }

}
