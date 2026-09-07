package com.svi.tictactoewebservice.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RoomRequest {

    @NotBlank(message = "Room Code is required.")
    @Pattern(
            regexp = "^[A-F0-9]{6}$",
            message = "Room code must be a valid 6-character code."
    )
    private String roomCode;

    public RoomRequest() {
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}