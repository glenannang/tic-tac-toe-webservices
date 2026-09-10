package com.svi.tictactoewebservice.dto.request;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class MoveRequest {

    private UUID gameid;

    @NotBlank(message = "Symbol is required.")
    @Pattern(regexp = "[XO]",
             message = "Symbol must be X or O.")
    private String symbol;

    @NotBlank(message = "Location is required.")
    @Pattern( regexp = "[0-8]",
              message = "Location must be between 0 and 8.")
    private String location;


    @NotBlank(message = "Player ID is required.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
             message = "Player ID must be a valid UUID.")
    private String playerid;


    public MoveRequest() {
    }

    public UUID getGameid() {
        return gameid;
    }

    public void setGameid(UUID gameid) {
        this.gameid = gameid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

}
