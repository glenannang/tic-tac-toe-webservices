package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.MoveRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    private final File recordsFolder = new File("records");

    public void saveMove(MoveRecord record) throws IOException {

        if (!recordsFolder.exists()) {
            recordsFolder.mkdir();
        }

        File playerFile = new File(
                recordsFolder,
                record.getPlayerid() + ".txt"
        );

        if (!playerFile.exists()) {
            playerFile.createNewFile();
        }

        try (FileWriter writer = new FileWriter(playerFile, true)) {
            writer.write(record.getGameid());
            writer.write(System.lineSeparator());
        }

        File gameFile = new File(
                recordsFolder,
                record.getGameid() + ".txt"
        );

        if (!gameFile.exists()) {
            gameFile.createNewFile();
        }

        try (FileWriter writer = new FileWriter(gameFile, true)) {
            writer.write(
                    record.getGameid() + "," +
                            record.getPlayerid() + "," +
                            record.getSymbol() + "," +
                            record.getLocation() + "," +
                            record.getDatesave()
            );

            writer.write(System.lineSeparator());
        }
    }

    public List<String> findGamesByPlayerId(String playerId) throws IOException {

        File playerFile = new File(recordsFolder, playerId + ".txt");

        if (!playerFile.exists()) {
            return null;
        }

        List<String> games = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(playerFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    games.add(line.trim());
                }
            }
        }
        return games;
    }

    public List<MoveRecord> findMovesByGameId(String gameId) throws IOException {

        File gameFile = new File(recordsFolder, gameId + ".txt");

        if (!gameFile.exists()) {
            return null;
        }

        List<MoveRecord> moves = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(gameFile))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",", 5);

                if (fields.length != 5) {
                    throw new IOException("Invalid game record format");
                }

                MoveRecord move = new MoveRecord();

                move.setGameid(fields[0].trim());
                move.setPlayerid(fields[1].trim());
                move.setSymbol(fields[2].trim());
                move.setLocation(fields[3].trim());
                move.setDatesave(fields[4].trim());

                moves.add(move);
            }
        }

        return moves;
    }
}