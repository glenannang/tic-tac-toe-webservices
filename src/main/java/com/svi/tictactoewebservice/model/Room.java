package com.svi.tictactoewebservice.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String roomCode;
    private List<String> gameIds = new ArrayList<>();

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public List<String> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<String> gameIds) {
        this.gameIds = gameIds;
    }
}