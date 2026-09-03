package com.svi.tictactoewebservice.dto.response;
import java.util.List;

public class RoomResponse {

    private String roomCode;
    private List<String> gameIds;

    public RoomResponse() {
    }

    public RoomResponse(String roomCode, List<String> gameIds) {
        this.roomCode = roomCode;
        this.gameIds = gameIds;
    }

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
