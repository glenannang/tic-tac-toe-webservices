package com.svi.tictactoewebservice.service.impl;

import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.dto.response.RoomResponse;
import com.svi.tictactoewebservice.enums.Message;
import com.svi.tictactoewebservice.exception.InternalServerException;
import com.svi.tictactoewebservice.exception.NotFoundException;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRecordRepository;
import com.svi.tictactoewebservice.repository.impl.RoomRepository;
import com.svi.tictactoewebservice.service.RoomService;
import com.svi.tictactoewebservice.repository.impl.CassandraRepository;


import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomServiceImpl implements RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomRecordRepository roomRepository = new RoomRepository();
    //new CassandraRepository(CassandraConnection.getInstance().getSession());

    @Override
    public RoomResponse getRoom(String roomCode){

        logger.info("Retrieving room with roomCode={}", roomCode);

        try {
            Room room = roomRepository.findRoom(roomCode);

            if (room == null) {

                logger.warn("Room not found with roomCode={}", roomCode);
                throw new NotFoundException(Message.ROOM_NOT_FOUND.getMessage());
            }

            return new RoomResponse(room.getRoomCode(), room.getGameIds());

        } catch (IOException e) {

            logger.error("Failed to retrieve room with roomCode={}", roomCode, e);
            throw new InternalServerException(Message.INTERNAL_SERVER_ERROR.getMessage(), e);
        }
    }

    @Override
    public void addGameToRoom(String roomCode, UUID gameId){
        logger.info("Adding gameId={} to roomCode={}", gameId, roomCode);

        try {
            roomRepository.addGameToRoom(roomCode, gameId);
            logger.info("GameId={} successfully added to roomCode={}", gameId, roomCode);

        } catch (IOException e) {

            logger.error("Failed to add gameId={} to roomCode={}", gameId, roomCode, e);
            throw new InternalServerException(Message.INTERNAL_SERVER_ERROR.getMessage(), e);
        }
    }
}