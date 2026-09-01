package com.svi.tictactoewebservice.service;
import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.repository.GameRepository;

import java.io.IOException;

public class GameService {
    private final GameRepository gameRepository = new GameRepository();

    public void saveMove(MoveRecord record) throws IOException {
        gameRepository.saveMove(record);
    }

}
