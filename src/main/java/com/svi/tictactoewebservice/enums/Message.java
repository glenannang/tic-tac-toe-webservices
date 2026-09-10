package com.svi.tictactoewebservice.enums;

public enum Message {

    RECORD_SAVED("Record saved."),
    RECORD_SAVE_FAILED("Record could not be saved."),
    RECORD_NOT_FOUND("Record not found."),
    RECORDS_FOUND("Records found"),
    ROOM_NOT_FOUND("Room not found."),
    INTERNAL_SERVER_ERROR("The server ran into an unexpected exception."),

    FIRST_MOVE_MUST_BE_X("First move must be X."),
    LOCATION_OCCUPIED("Location is already occupied."),
    GAME_PLAYER_LIMIT("Game already has two players."),
    PLAYER_SYMBOL_CHANGE("Player cannot change symbols."),
    SYMBOL_ALREADY_ASSIGNED("Symbol is already assigned to another player."),
    GAME_ALREADY_FINISHED("Game is already finished."),

    INVALID_TURN("Invalid turn. Expected %s.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}