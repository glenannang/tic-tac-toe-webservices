package com.svi.tictactoewebservice.validator;

public class IdValidator {
    public void validateGameId(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new IllegalArgumentException("Game ID is required.");
        }
    }

    public void validatePlayerId(String playerId) {
        if (playerId == null || playerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Player ID is required.");
        }
    }

    public void validateRoomId(String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID is required.");
        }
    }

}
