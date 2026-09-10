package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.MoveRecord;

import java.io.IOException;
import java.util.List;

public interface GameRecordRepository {

    void saveMove(MoveRecord record) throws IOException;

    List<MoveRecord> findMovesByGameId(String gameId) throws IOException;

    List<String> findGamesByPlayerId(String playerId) throws IOException;
}