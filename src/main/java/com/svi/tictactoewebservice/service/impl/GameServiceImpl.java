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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private final RoomServiceImpl roomService = new RoomServiceImpl();
    private final MoveValidator moveValidator = new MoveValidator();
    private final GameRecordRepository gameRepository = new GameRepository();

    //new CassandraRepository(CassandraConnection.getInstance().getSession());


    @Override
    public ApiResponse saveMove(MoveRequest request) {
            logger.info("Saving move for gameId={}, playerId={}", request.getGameid(), request.getPlayerid());

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

                logger.info("Move saved successfully for gameId={}", request.getGameid());

                return new ApiResponse(Message.RECORD_SAVED.getMessage());

            } catch (IllegalArgumentException e) {

                logger.warn("Invalid move for gameId={}: {}", request.getGameid(), e.getMessage());

                throw new BadRequestException(e.getMessage());

            } catch (IOException e) {
                logger.error("Failed to save move for gameId={}", request.getGameid(), e);
                throw new InternalServerException(Message.RECORD_SAVE_FAILED.getMessage(), e);
            }
    }


    @Override
    public GameDetailsResponse getGameDetails(UUID gameId) {
        logger.info("Retrieving game details for gameId={}", gameId);

        try {
            List<MoveRecord> moves = gameRepository.findMovesByGameId(gameId);

            if (moves == null || moves.isEmpty()) {
                logger.warn("Game not found for gameId={}", gameId);
                throw new NotFoundException(Message.RECORD_NOT_FOUND.getMessage());
            }

            return new GameDetailsResponse(moves, Message.RECORDS_FOUND.getMessage());

        } catch (IOException e) {
            logger.error("Failed to retrieve game details for gameId={}", gameId, e);
            throw new InternalServerException(Message.INTERNAL_SERVER_ERROR.getMessage(), e);
        }

    }

    @Override
    public GameIdResponse createGameRecord(String roomCode){

        logger.info("Creating game record for roomCode={}", roomCode);

        try {
            UUID gameId = UUID.randomUUID();
            roomService.addGameToRoom(roomCode, gameId);

            logger.info("Game {} added to room {}", gameId, roomCode);

            return new GameIdResponse(gameId);

        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());

        }
    }


}
