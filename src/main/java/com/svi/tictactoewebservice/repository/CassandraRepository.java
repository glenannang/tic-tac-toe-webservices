package com.svi.tictactoewebservice.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
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

    public CassandraRepository(Session session,String tableName) {
        this.session = session;

        this.insertMoveStatement = this.session.prepare(
                  "INSERT INTO " + tableName +
                        " (game_id, date_save, player_id, symbol, location) " +
                        "VALUES (?, ?, ?, ?, ?)"
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

}