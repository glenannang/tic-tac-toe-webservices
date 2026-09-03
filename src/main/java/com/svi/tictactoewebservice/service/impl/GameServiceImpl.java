package com.svi.tictactoewebservice.service.impl;
import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.repository.GameRepository;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.validator.IdValidator;
import com.svi.tictactoewebservice.validator.MoveRequestValidator;
import com.svi.tictactoewebservice.dto.response.GameIdResponse;
import com.svi.tictactoewebservice.dto.response.RoomResponse;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository = new GameRepository();
    private final MoveRequestValidator moveRequestValidator = new MoveRequestValidator();
    private final IdValidator idValidator = new IdValidator();
    private final RoomServiceImpl roomService = new RoomServiceImpl();

    @Override
    public void saveMove(MoveRequest request) throws IOException {

        moveRequestValidator.validate(request);

        MoveRecord record = new MoveRecord();
        record.setGameid(request.getGameid());
        record.setPlayerid(request.getPlayerid());
        record.setSymbol(request.getSymbol());
        record.setLocation(request.getLocation());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        record.setDatesave(LocalDateTime.now().format(formatter));

        gameRepository.saveMove(record);
    }
    @Override
    public List<String> getPlayerGames(String playerId) throws IOException {
        idValidator.validatePlayerId(playerId);
        return gameRepository.findGamesByPlayerId(playerId);
    }

    @Override
    public List<MoveRecord> getGameDetails(String gameId) throws IOException {
        idValidator.validateGameId(gameId);
        return gameRepository.findMovesByGameId(gameId);
    }

    @Override
    public GameIdResponse createGameRecord(String roomCode) throws IOException {
        RoomResponse room = roomService.getRoom(roomCode);

        if (room == null) {
            return null;
        }

        String gameId = generateGameId();
        roomService.addGameToRoom(roomCode,gameId);

        return new GameIdResponse(gameId);

    }


    private String generateGameId() {
        return UUID.randomUUID().toString();
    }

}
