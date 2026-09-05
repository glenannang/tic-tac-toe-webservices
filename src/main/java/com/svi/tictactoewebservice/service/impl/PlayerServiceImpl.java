package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.repository.GameRepository;
import com.svi.tictactoewebservice.repository.RoomRepository;
import com.svi.tictactoewebservice.service.PlayerService;
import com.svi.tictactoewebservice.validator.IdValidator;
import com.svi.tictactoewebservice.model.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerServiceImpl implements PlayerService {

    private final GameRepository gameRepository = new GameRepository();
    private final RoomRepository roomRepository = new RoomRepository();
    private final IdValidator idValidator = new IdValidator();

    @Override
    public List<String> getPlayerGames(String playerId) throws IOException {
        idValidator.validatePlayerId(playerId);
        return gameRepository.findGamesByPlayerId(playerId);
    }

    @Override
    public List<Room> getPlayerRooms(String playerId) throws IOException {
        idValidator.validatePlayerId(playerId);

        List<String> playerGames = gameRepository.findGamesByPlayerId(playerId);
        List<Room> allRooms = roomRepository.findAllRooms();
        List<Room> playerRooms = new ArrayList<>();

        if (playerGames == null) {
            return playerRooms;
        }

        for (Room room : allRooms) {
            List<String> matchingGames = new ArrayList<>();

            for (String gameId : room.getGameIds()) {
                if (playerGames.contains(gameId)) {
                    matchingGames.add(gameId);
                }
            }

            if (!matchingGames.isEmpty()) {
                Room playerRoom = new Room();
                playerRoom.setRoomCode(room.getRoomCode());
                playerRoom.setGameIds(matchingGames);
                playerRooms.add(playerRoom);
            }
        }

        return playerRooms;
    }
}