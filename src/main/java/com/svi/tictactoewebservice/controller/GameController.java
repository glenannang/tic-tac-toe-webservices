package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.dto.response.*;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.service.impl.GameServiceImpl;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.UUID;


@Path("/game")
public class GameController {

    private final GameService gameService = new GameServiceImpl();

    // Saves a player's move in a game
    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid MoveRequest request) {
            ApiResponse response = gameService.saveMove(request);
            return Response.ok(response).build();
    }

    // Retrieves all move records for a specific game
    @GET
    @Path("/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("gameId") UUID gameId) {
            GameDetailsResponse response = gameService.getGameDetails(gameId);
            return Response.ok(response).build();
    }

}