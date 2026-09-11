package com.svi.tictactoewebservice.service.impl;
import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.dto.response.*;
import com.svi.tictactoewebservice.enums.Message;
import com.svi.tictactoewebservice.exception.BadRequestException;
import com.svi.tictactoewebservice.exception.InternalServerException;
import com.svi.tictactoewebservice.exception.NotFoundException;
import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.repository.GameRecordRepository;
import com.svi.tictactoewebservice.repository.impl.CassandraRepository;
import com.svi.tictactoewebservice.repository.impl.GameRepository;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.validator.MoveValidator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class GameServiceImpl implements GameService {

    private final RoomServiceImpl roomService = new RoomServiceImpl();
    private final MoveValidator moveValidator = new MoveValidator();
    private final GameRecordRepository gameRepository = new GameRepository();

    //new CassandraRepository(CassandraConnection.getInstance().getSession());


    @Override
    public ApiResponse saveMove(MoveRequest request) {

            try {
                List<MoveRecord> existingMoves = gameRepository.findMovesByGameId(request.getGameid());
                moveValidator.validate(request, existingMoves); //can throw IllegalArgumentException

                //convert move request to moverecord
                MoveRecord record = new MoveRecord();
                record.setGameid(request.getGameid());
                record.setPlayerid(request.getPlayerid());
                record.setSymbol(request.getSymbol());
                record.setLocation(request.getLocation());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                record.setDatesave(LocalDateTime.now().format(formatter));

                gameRepository.saveMove(record);
                return new ApiResponse(Message.RECORD_SAVED.getMessage());

            } catch (IllegalArgumentException e) {
                throw new BadRequestException(e.getMessage());

            } catch (IOException e) {
                throw new InternalServerException(Message.RECORD_SAVE_FAILED.getMessage(), e);
            }
    }


    @Override
    public GameDetailsResponse getGameDetails(UUID gameId) {
        try {
            List<MoveRecord> moves =
                    gameRepository.findMovesByGameId(gameId);

            if (moves == null || moves.isEmpty()) {
                throw new NotFoundException(Message.RECORD_NOT_FOUND.getMessage());
            }

            return new GameDetailsResponse(moves, Message.RECORDS_FOUND.getMessage());

        } catch (IOException e) {
            throw new InternalServerException(Message.INTERNAL_SERVER_ERROR.getMessage(), e);
        }

    }

    @Override
    public GameIdResponse createGameRecord(String roomCode){

        try {
            UUID gameId = UUID.randomUUID();
            roomService.addGameToRoom(roomCode, gameId);
            return new GameIdResponse(gameId);

        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());

        }
    }


}
