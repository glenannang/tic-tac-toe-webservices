package com.svi.tictactoewebservice.service;
import com.svi.tictactoewebservice.model.Room;
import java.io.IOException;
import java.util.List;

public interface PlayerService {
    List<String> getPlayerGames(String playerId) throws IOException;
    List<Room> getPlayerRooms(String playerId) throws IOException;
}