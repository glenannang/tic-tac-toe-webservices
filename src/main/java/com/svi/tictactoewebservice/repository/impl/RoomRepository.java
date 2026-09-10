package com.svi.tictactoewebservice.repository.impl;

import com.svi.tictactoewebservice.config.Config;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.repository.RoomRecordRepository;
import com.svi.tictactoewebservice.service.FileStorageService;
import com.svi.tictactoewebservice.service.impl.FileStorageServiceImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomRepository implements RoomRecordRepository {

    private final File roomsFolder = new File(Config.get(Config.Keys.RECORDS_DIR.value()), Config.get(Config.Keys.ROOM_DIR.value()));
    private final FileStorageService fileStorageService = new FileStorageServiceImpl();



    public void addGameToRoom(String roomCode, UUID gameId) throws IOException {

        File roomFile = new File(roomsFolder, roomCode + ".txt");

            if (!roomFile.exists()) {
                roomFile.createNewFile();
            }

        fileStorageService.appendLine(roomFile, gameId.toString());
    }

    public Room findRoom(String roomCode) throws IOException {

        File roomFile = new File(roomsFolder, roomCode + ".txt");

        if (!roomFile.exists()) {
            return null;
        }

        List<String> lines = fileStorageService.readLines(roomFile);
        List<String> gameIds = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                gameIds.add(line.trim());
            }
        }

        Room room = new Room();
        room.setRoomCode(roomCode);
        room.setGameIds(gameIds);

        return room;
    }

    public List<Room> findAllRooms() throws IOException {
        List<Room> rooms = new ArrayList<>();

        if (!roomsFolder.exists()) {
            return rooms;
        }

        File[] roomFiles = roomsFolder.listFiles(
                (dir, name) -> name.endsWith(".txt")
        );

        if (roomFiles == null) {
            return rooms;
        }

        for (File roomFile : roomFiles) {
            String fileName = roomFile.getName();
            String roomCode = fileName.substring(0, fileName.length() - 4);

            Room room = findRoom(roomCode);

            if (room != null) {
                rooms.add(room);
            }
        }

        return rooms;
    }

}