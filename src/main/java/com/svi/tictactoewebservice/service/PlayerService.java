package com.svi.tictactoewebservice.service;

import java.io.IOException;
import java.util.List;

public interface PlayerService {

    List<String> getPlayerGames(String playerId) throws IOException;
}