package com.svi.tictactoewebservice.validator;

import java.util.UUID;

public class IdValidator {

    public void validateGameId(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new IllegalArgumentException("Game ID is required.");
        }

        if (!gameId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("Game ID must be a valid UUID.");
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
        if (!playerId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("Player ID must be a valid UUID.");
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
