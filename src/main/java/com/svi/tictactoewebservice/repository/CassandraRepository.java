package com.svi.tictactoewebservice.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.ConfigLoader;
import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.model.Room;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CassandraRepository {

    private final Session session;
    private final PreparedStatement insertMoveStatement;
    private final PreparedStatement findMovesByGameIdStatement;
    private final PreparedStatement insertPlayerGameStatement;
    private final PreparedStatement findGamesByPlayerIdStatement;
    private final PreparedStatement findRoomByCodeStatement;
    private final PreparedStatement insertRoomGameStatement;
    private final PreparedStatement findGamesByRoomCodeStatement;
    private final PreparedStatement findAllRoomsStatement;

    private final PreparedStatement insertRoomStatement;


    public CassandraRepository(Session session) {
        this.session = session;
        ConfigLoader config = ConfigLoader.getInstance();
        String tableName = config.getCassandraTable();
        String playerGamesTable = config.getPlayerGamesTable();
        String roomsTable = config.getRoomsTable();
        String roomGamesTable = config.getRoomGamesTable();


        this.insertPlayerGameStatement = this.session.prepare(
                "INSERT INTO " + playerGamesTable +
                        " (player_id, date_saved, game_id) VALUES (?, ?, ?)"
        );

        this.insertMoveStatement = this.session.prepare(
                  "INSERT INTO " + tableName +
                        " (game_id, date_save, player_id, symbol, location) " +
                        "VALUES (?, ?, ?, ?, ?)"
        );

        this.findGamesByPlayerIdStatement = this.session.prepare(
                "SELECT game_id, date_saved FROM " + playerGamesTable +
                        " WHERE player_id = ?"
        );

        this.findMovesByGameIdStatement = this.session.prepare(
                "SELECT * FROM " + tableName + " WHERE game_id = ?"
        );

        this.insertRoomStatement = session.prepare("INSERT INTO " + roomsTable + " (room_code) VALUES (?)"
        );


        this.insertRoomGameStatement = session.prepare(
                "INSERT INTO " + roomGamesTable +
                        " (room_code, date_saved, game_id) VALUES (?, ?, ?)"
        );

        this.findRoomByCodeStatement = session.prepare(
                "SELECT room_code FROM " + roomsTable +
                        " WHERE room_code = ?"
        );
        this.findGamesByRoomCodeStatement = session.prepare(
                "SELECT game_id FROM " + roomGamesTable +
                        " WHERE room_code = ?"
        );
        this.findAllRoomsStatement = session.prepare(
                "SELECT room_code FROM " + roomsTable
        );



    }


    //for saving moves
    public void saveMove(MoveRecord record) {
        UUID gameId = UUID.fromString(record.getGameid());
        UUID playerId = UUID.fromString(record.getPlayerid());
        int location = Integer.parseInt(record.getLocation());

        LocalDateTime localDateTime = LocalDateTime.parse(record.getDatesave(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Date dateSave = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        session.execute(insertMoveStatement.bind(gameId, dateSave, playerId, record.getSymbol(), location));

        if (!playerGameExists(playerId, gameId)) {
            session.execute(insertPlayerGameStatement.bind(playerId, dateSave, gameId));
        }
    }

    private boolean playerGameExists(UUID playerId, UUID gameId) {
        ResultSet resultSet = session.execute(findGamesByPlayerIdStatement.bind(playerId));

        for (Row row : resultSet) {
            if (gameId.equals(row.getUUID("game_id"))) {
                return true;
            }
        }

        return false;
    }

    public List<MoveRecord> findMovesByGameId(String gameId) {

        ResultSet resultSet = session.execute(findMovesByGameIdStatement.bind(UUID.fromString(gameId)));

        List<MoveRecord> moves = new ArrayList<>();

        // convert back to strings
        for (Row row : resultSet) {
            MoveRecord record = new MoveRecord();

            record.setGameid(row.getUUID("game_id").toString());
            record.setPlayerid(row.getUUID("player_id").toString());
            record.setSymbol(row.getString("symbol"));
            record.setLocation(String.valueOf(row.getInt("location")));

            Date date = row.getTimestamp("date_save");

            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    date.toInstant(),
                    ZoneId.systemDefault()
            );

            record.setDatesave(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            moves.add(record);
        }
        return moves;
    }


    public List<String> findGamesByPlayerId(String playerId) {

        UUID playerUuid = UUID.fromString(playerId);

        ResultSet resultSet = session.execute(findGamesByPlayerIdStatement.bind(playerUuid));

        List<String> gameIds = new ArrayList<>();

        for (Row row : resultSet) {
            gameIds.add(row.getUUID("game_id").toString());
        }

        return gameIds;
    }


    public void addGameToRoom(String roomCode, String gameId) throws IOException {

        UUID gameUuid = UUID.fromString(gameId);
        Date dateSaved = new Date();

        session.execute(insertRoomGameStatement.bind(roomCode, dateSaved, gameUuid));
    }

    public Room findRoom(String roomCode) {

        Row roomRow = session.execute(findRoomByCodeStatement.bind(roomCode)).one();

        if (roomRow == null) {
            return null;
        }

        ResultSet resultSet = session.execute(findGamesByRoomCodeStatement.bind(roomCode));

        List<String> gameIds = new ArrayList<>();

        for (Row row : resultSet) {
            gameIds.add(row.getUUID("game_id").toString());
        }

        Room room = new Room();
        room.setRoomCode(roomCode);
        room.setGameIds(gameIds);

        return room;
    }

    public List<Room> findAllRooms() {

        ResultSet resultSet = session.execute(findAllRoomsStatement.bind());
        List<Room> rooms = new ArrayList<>();

        for (Row row : resultSet) {
            String roomCode = row.getString("room_code");
            Room room = findRoom(roomCode);

            if (room != null) {
                rooms.add(room);
            }
        }
        return rooms;
    }


}