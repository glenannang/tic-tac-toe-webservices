package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final File roomsFolder = new File("records", "rooms");

    public void createRoom(Room room) throws IOException {

        if (!roomsFolder.exists()) {
            roomsFolder.mkdirs();
        }

        File roomFile = new File(roomsFolder, room.getRoomCode() + ".txt");

        if (!roomFile.exists()) {
            roomFile.createNewFile();
        }
    }

    public void addGameToRoom(String roomCode, String gameId) throws IOException {

        File roomFile = new File(roomsFolder, roomCode + ".txt");

        if (!roomFile.exists()) {
            throw new IOException("Room does not exist.");
        }

        try (FileWriter writer = new FileWriter(roomFile, true)) {
            writer.write(gameId);
            writer.write(System.lineSeparator());
        }
    }

    public Room findRoom(String roomCode) throws IOException {

        File roomFile = new File(roomsFolder, roomCode + ".txt");

        if (!roomFile.exists()) {
            return null;
        }

        List<String> gameIds = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(roomFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    gameIds.add(line.trim());
                }
            }
        }

        Room room = new Room();
        room.setRoomCode(roomCode);
        room.setGameIds(gameIds);

        return room;
    }
}