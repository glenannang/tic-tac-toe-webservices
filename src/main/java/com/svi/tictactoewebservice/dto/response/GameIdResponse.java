package com.svi.tictactoewebservice.dto.response;

import java.util.UUID;

public class GameIdResponse {

    private UUID gameId;

    public GameIdResponse() {
    }

    public GameIdResponse(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

}
