package com.svi.tictactoewebservice.repository;

import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.config.Config;
import com.svi.tictactoewebservice.service.FileStorageService;
import com.svi.tictactoewebservice.service.impl.FileStorageServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    private final FileStorageService fileStorageService = new FileStorageServiceImpl();

    private final File playerFolder = new File(
            Config.get(Config.Keys.RECORDS_DIR.value()),
            Config.get(Config.Keys.PLAYER_DIR.value())
    );

    private final File gameFolder = new File(
            Config.get(Config.Keys.RECORDS_DIR.value()),
            Config.get(Config.Keys.GAME_DIR.value())
    );

    public void saveMove(MoveRecord record) throws IOException {

        fileStorageService.createDirectory(playerFolder);
        fileStorageService.createDirectory(gameFolder);

        File playerFile = new File(playerFolder, record.getPlayerid() + ".txt");

        fileStorageService.createFile(playerFile);

        boolean gameAlreadyExists = false;

        List<String> playerGames = fileStorageService.readLines(playerFile);

        for (String line : playerGames) {
            if (line.trim().equals(record.getGameid())) {
                gameAlreadyExists = true;
                break;
            }
        }

        if (!gameAlreadyExists) {
            fileStorageService.appendLine(playerFile, record.getGameid());
        }

        File gameFile = new File(gameFolder, record.getGameid() + ".txt");

        fileStorageService.createFile(gameFile);

        String moveData =
                        record.getGameid() + "," +
                        record.getPlayerid() + "," +
                        record.getSymbol() + "," +
                        record.getLocation() + "," +
                        record.getDatesave();

        fileStorageService.appendLine(gameFile, moveData);

    }

    public List<String> findGamesByPlayerId(String playerId) throws IOException {

        File playerFile = new File(playerFolder, playerId + ".txt");

        if (!playerFile.exists()) {
            return null;
        }

        List<String> lines = fileStorageService.readLines(playerFile);
        List<String> games = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                games.add(line.trim());
            }
        }

        return games;
    }

    public List<MoveRecord> findMovesByGameId(String gameId) throws IOException {

        File gameFile = new File(gameFolder, gameId + ".txt");

        if (!gameFile.exists()) {
            return null;
        }

        List<String> lines = fileStorageService.readLines(gameFile);
        List<MoveRecord> moves = new ArrayList<>();

        for (String line : lines) {

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

        return moves;
    }
}