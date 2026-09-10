package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.response.ErrorResponse;
import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.dto.response.RoomListResponse;
import com.svi.tictactoewebservice.service.PlayerService;
import com.svi.tictactoewebservice.service.impl.PlayerServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.UUID;


@Path("/player")
public class PlayerController {

    private final PlayerService playerService = new PlayerServiceImpl();

    // Retrieves all games participated in by a player
    @GET
    @Path("/{playerId}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId") UUID playerId) {

            GameListResponse response = playerService.getPlayerGames(playerId);
            return Response.ok(response).build();
    }

    // Retrieves all rooms participated in by the specified player, including their games in each room
    @GET
    @Path("/{playerId}/rooms")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRooms(@PathParam("playerId") UUID playerId) {

            RoomListResponse response = playerService.getPlayerRooms(playerId);
            return Response.ok(response).build();

    }
}