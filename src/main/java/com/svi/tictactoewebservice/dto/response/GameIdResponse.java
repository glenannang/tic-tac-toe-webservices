package com.svi.tictactoewebservice.dto.response;

public class GameIdResponse {

    private String gameId;

    public GameIdResponse() {
    }

    public GameIdResponse(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

}
