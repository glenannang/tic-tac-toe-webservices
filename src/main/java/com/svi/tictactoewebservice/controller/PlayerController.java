package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.dto.response.ErrorResponse;
import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.service.impl.PlayerServiceImpl;
import com.svi.tictactoewebservice.model.Room;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/player")
public class PlayerController {

    private final PlayerServiceImpl playerService = new PlayerServiceImpl();

    @GET
    @Path("/{playerId}/games")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(
            @PathParam("playerId") String playerId,
            PlayerRequest request
    ) {

        try {
            List<String> games = playerService.getPlayerGames(playerId);

            if (games == null) {
                return Response.status(402)
                        .entity(new ErrorResponse("Record not found"))
                        .build();
            }

            List<GameListResponse.GameId> gameList = new ArrayList<>();

            for (String gameId : games) {
                gameList.add(new GameListResponse.GameId(gameId));
            }

            return Response.ok(
                    new GameListResponse(gameList, "Records found")
            ).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();

        } catch (IOException e) {
            return Response.status(500)
                    .entity(new ErrorResponse(
                            "The server ran into an unexpected exception."
                    ))
                    .build();
        }
    }

    @GET
    @Path("/{playerId}/rooms")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRooms(
            @PathParam("playerId") String playerId,
            PlayerRequest request
    ) {
        try {
            List<Room> rooms = playerService.getPlayerRooms(playerId);

            if (rooms.isEmpty()) {
                return Response.status(402)
                        .entity(new ErrorResponse("Record not found"))
                        .build();
            }

            return Response.ok(rooms).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();

        } catch (IOException e) {
            return Response.status(500)
                    .entity(new ErrorResponse(
                            "The server ran into an unexpected exception."
                    ))
                    .build();
        }
    }






}