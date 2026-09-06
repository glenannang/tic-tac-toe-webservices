package com.svi.tictactoewebservice.validator;

import java.util.UUID;

public class IdValidator {

    public void validateGameId(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new IllegalArgumentException("Game ID is required.");
        }

        try {
            UUID.fromString(gameId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Game ID must be a valid UUID.");
        }

    }

    public void validatePlayerId(String playerId) {
        if (playerId == null || playerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Player ID is required.");
        }

        try {
            UUID.fromString(playerId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Player ID must be a valid UUID.");
        }
    }

    public void validateRoomId(String roomId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room Code is required.");
        }

        if (!roomId.matches("^[A-F0-9]{6}$")) {
            throw new IllegalArgumentException("Room code must be a valid 6-character code.");
        }
    }

}
