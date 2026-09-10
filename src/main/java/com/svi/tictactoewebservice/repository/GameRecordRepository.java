package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.MoveRecord;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface GameRecordRepository {

    void saveMove(MoveRecord record) throws IOException;

    List<MoveRecord> findMovesByGameId(UUID gameId) throws IOException;

    List<String> findGamesByPlayerId(UUID playerId) throws IOException;
}