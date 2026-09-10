package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.dto.response.RoomListResponse;
import com.svi.tictactoewebservice.repository.CassandraRepository;
import com.svi.tictactoewebservice.repository.GameRepository;
import com.svi.tictactoewebservice.repository.RoomRepository;
import com.svi.tictactoewebservice.service.PlayerService;
import com.svi.tictactoewebservice.validator.IdValidator;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.dto.response.RoomResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerServiceImpl implements PlayerService {

    private final GameRepository gameRepository = new GameRepository();
    private final RoomRepository roomRepository = new RoomRepository();
    private final CassandraRepository cassandraRepository = new CassandraRepository(CassandraConnection.getInstance().getSession());


    @Override
    public GameListResponse getPlayerGames(String playerId) throws IOException {
        //List<String> games = gameRepository.findGamesByPlayerId(playerId);
        List<String> games = cassandraRepository.findGamesByPlayerId(playerId);

        if (games == null) {
            return null;
        }

        List<GameListResponse.GameId> gameList = new ArrayList<>();

        for (String gameId : games) {
            gameList.add(new GameListResponse.GameId(gameId));
        }

        return new GameListResponse(gameList, "Records found");
    }

    @Override
    public RoomListResponse getPlayerRooms(String playerId) throws IOException  {

        //List<String> playerGames = gameRepository.findGamesByPlayerId(playerId);
        List<String> playerGames = cassandraRepository.findGamesByPlayerId(playerId);
        //List<Room> allRooms = roomRepository.findAllRooms();
        List<Room> allRooms = cassandraRepository.findAllRooms();

        List<RoomResponse> roomList = new ArrayList<>();

        if (playerGames == null) {
            return null;
        }

        for (Room room : allRooms) {
            List<String> matchingGames = new ArrayList<>();

            for (String gameId : room.getGameIds()) {
                if (playerGames.contains(gameId)) {
                    matchingGames.add(gameId);
                }
            }

            if (!matchingGames.isEmpty()) {
                roomList.add(new RoomResponse(room.getRoomCode(), matchingGames));
            }
        }

        return new RoomListResponse(roomList, "Records found");
    }
}