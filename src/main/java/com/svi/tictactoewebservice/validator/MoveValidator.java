package com.svi.tictactoewebservice.validator;

import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.enums.Message;
import com.svi.tictactoewebservice.model.MoveRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoveValidator {

    public void validate( MoveRequest request, List<MoveRecord> existingMoves) {

        if (existingMoves == null || existingMoves.isEmpty()) {
            validateFirstMove(request);
            return;
        }

        validateGameNotFinished(existingMoves); //check first if the game is finished before processing move
        validateLocationAvailable(request, existingMoves);
        validateTurn(request, existingMoves);
        validatePlayerLimit(request,existingMoves);
        validatePlayerSymbol(request, existingMoves);
        validateSymbolOwner(request, existingMoves);

    }

    private void validateFirstMove(MoveRequest request) {
        if (!"X".equals(request.getSymbol())) {
            throw new IllegalArgumentException(Message.FIRST_MOVE_MUST_BE_X.getMessage());
        }
    }

    private void validateLocationAvailable(MoveRequest request, List<MoveRecord> existingMoves) {

        for (MoveRecord move : existingMoves) {
            if (move.getLocation().equals(request.getLocation())) {
                throw new IllegalArgumentException(Message.LOCATION_OCCUPIED.getMessage());
            }
        }
    }

    private void validateTurn(MoveRequest request, List<MoveRecord> existingMoves) {
        MoveRecord lastMove = existingMoves.get(existingMoves.size() - 1);

        String expectedSymbol = "X".equals(lastMove.getSymbol()) ? "O" : "X";

        if (!expectedSymbol.equals(request.getSymbol())) {
            throw new IllegalArgumentException(String.format(Message.INVALID_TURN.getMessage(), expectedSymbol));
        }
    }

    private void validatePlayerLimit(MoveRequest request, List<MoveRecord> existingMoves) {
        List<UUID> playerIds = new ArrayList<>();

        for (MoveRecord move : existingMoves) {
            if (!playerIds.contains(move.getPlayerid())) {
                playerIds.add(move.getPlayerid());
            }
        }

        if (!playerIds.contains(request.getPlayerid()) && playerIds.size() >= 2) {
            throw new IllegalArgumentException(Message.GAME_PLAYER_LIMIT.getMessage());
        }
    }

    private void validatePlayerSymbol(MoveRequest request, List<MoveRecord> existingMoves) {
        for (MoveRecord move : existingMoves) {
            if (move.getPlayerid().equals(request.getPlayerid()) && !move.getSymbol().equals(request.getSymbol())) {
                throw new IllegalArgumentException(Message.PLAYER_SYMBOL_CHANGE.getMessage());
            }
        }
    }

    private void validateSymbolOwner(MoveRequest request, List<MoveRecord> existingMoves) {
        for (MoveRecord move : existingMoves) {

            if (move.getSymbol().equals(request.getSymbol()) && !move.getPlayerid().equals(request.getPlayerid())) {
                throw new IllegalArgumentException(Message.SYMBOL_ALREADY_ASSIGNED.getMessage());
            }

        }
    }

    private void validateGameNotFinished(List<MoveRecord> existingMoves) {

        String[] board = new String[9];

        for (MoveRecord move : existingMoves) {
            int location = Integer.parseInt(move.getLocation());
            board[location] = move.getSymbol();
        }

        if (hasWinner(board) || existingMoves.size() >= 9) {
            throw new IllegalArgumentException(Message.GAME_ALREADY_FINISHED.getMessage());
        }

    }

    private boolean hasWinner(String[] board) {

        int[][] winningCombinations = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };

        for (int[] combination : winningCombinations) {

            int first = combination[0];
            int second = combination[1];
            int third = combination[2];

            if (board[first] != null && board[first].equals(board[second]) && board[first].equals(board[third])) {
                return true;
            }
        }

        return false;
    }


}
