package com.svi.tictactoewebservice.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.ConfigLoader;
import com.svi.tictactoewebservice.model.MoveRecord;
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


    public CassandraRepository(Session session) {
        this.session = session;
        ConfigLoader config = ConfigLoader.getInstance();
        String tableName = config.getCassandraTable();
        String playerGamesTable = config.getPlayerGamesTable();

        this.insertPlayerGameStatement = session.prepare(
                "INSERT INTO " + playerGamesTable +
                        " (player_id, date_saved, game_id) VALUES (?, ?, ?)"
        );

        this.insertMoveStatement = this.session.prepare(
                  "INSERT INTO " + tableName +
                        " (game_id, date_save, player_id, symbol, location) " +
                        "VALUES (?, ?, ?, ?, ?)"
        );

        this.findGamesByPlayerIdStatement = session.prepare(
                "SELECT game_id, date_saved FROM " + playerGamesTable +
                        " WHERE player_id = ?"
        );

        this.findMovesByGameIdStatement = this.session.prepare(
                "SELECT * FROM " + tableName + " WHERE game_id = ?"
        );


    }
    //for saving moves
    public void saveMove(MoveRecord record) {
        UUID gameId = UUID.fromString(record.getGameid());
        UUID playerId = UUID.fromString(record.getPlayerid());
        int location = Integer.parseInt(record.getLocation());

        LocalDateTime localDateTime = LocalDateTime.parse(
                record.getDatesave(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        Date dateSave = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        session.execute(
                insertMoveStatement.bind(
                        gameId,
                        dateSave,
                        playerId,
                        record.getSymbol(),
                        location
                )
        );

        //duplicated player and game row
        session.execute(
                insertPlayerGameStatement.bind(
                        playerId,
                        dateSave,
                        gameId
                )
        );
    }

    public List<MoveRecord> findMovesByGameId(String gameId) {

        ResultSet resultSet = session.execute(
                findMovesByGameIdStatement.bind(UUID.fromString(gameId))
        );

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

            record.setDatesave(
                    localDateTime.format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    )
            );

            moves.add(record);
        }
        return moves;
    }

    public List<String> findGamesByPlayerId(String playerId) {

        UUID playerUuid = UUID.fromString(playerId);

        ResultSet resultSet = session.execute(
                findGamesByPlayerIdStatement.bind(playerUuid)
        );

        List<String> gameIds = new ArrayList<>();

        for (Row row : resultSet) {
            gameIds.add(
                    row.getUUID("game_id").toString()
            );
        }

        return gameIds;
    }





}