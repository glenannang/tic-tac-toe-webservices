package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.dto.response.RoomListResponse;
import com.svi.tictactoewebservice.repository.GameRecordRepository;
import com.svi.tictactoewebservice.repository.RoomRecordRepository;
import com.svi.tictactoewebservice.repository.impl.CassandraRepository;
import com.svi.tictactoewebservice.repository.impl.GameRepository;
import com.svi.tictactoewebservice.repository.impl.RoomRepository;
import com.svi.tictactoewebservice.service.PlayerService;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.dto.response.RoomResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {

    private final GameRecordRepository gameRepository = new CassandraRepository(CassandraConnection.getInstance().getSession());
    private final RoomRecordRepository roomRepository = new CassandraRepository(CassandraConnection.getInstance().getSession());


    @Override
    public GameListResponse getPlayerGames(UUID playerId) throws IOException {

        List<UUID> games = gameRepository.findGamesByPlayerId(playerId);

        if (games == null) {
            return null;
        }

        List<GameListResponse.GameId> gameList = new ArrayList<>();

        for (UUID gameId : games) {
            gameList.add(new GameListResponse.GameId(gameId.toString()));
        }

        return new GameListResponse(gameList, "Records found");
    }

    @Override
    public RoomListResponse getPlayerRooms(UUID playerId) throws IOException  {

        List<UUID> playerGames = gameRepository.findGamesByPlayerId(playerId);
        List<Room> allRooms = roomRepository.findAllRooms();

        List<RoomResponse> roomList = new ArrayList<>();

        if (playerGames == null) {
            return null;
        }

        for (Room room : allRooms) {
            List<String> matchingGames = new ArrayList<>();

            for (String gameId : room.getGameIds()) {
                if (playerGames.contains(UUID.fromString(gameId))) {
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