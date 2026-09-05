package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.repository.GameRepository;
import com.svi.tictactoewebservice.service.PlayerService;
import com.svi.tictactoewebservice.validator.IdValidator;

import java.io.IOException;
import java.util.List;

public class PlayerServiceImpl implements PlayerService {

    private final GameRepository gameRepository = new GameRepository();
    private final IdValidator idValidator = new IdValidator();

    @Override
    public List<String> getPlayerGames(String playerId) throws IOException {
        idValidator.validatePlayerId(playerId);
        return gameRepository.findGamesByPlayerId(playerId);
    }
}