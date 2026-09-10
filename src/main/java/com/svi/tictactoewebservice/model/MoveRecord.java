package com.svi.tictactoewebservice.model;


import java.util.UUID;

public class MoveRecord {

    private UUID gameid;
    private String symbol;
    private String location;
    private UUID playerid;
    private String datesave;

    public MoveRecord() {
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

    public UUID getPlayerid() {
        return playerid;
    }

    public void setPlayerid(UUID playerid) {
        this.playerid = playerid;
    }

    public String getDatesave() {
        return datesave;
    }

    public void setDatesave(String datesave) {
        this.datesave = datesave;
    }
}