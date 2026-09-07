package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.response.ErrorResponse;
import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.dto.response.RoomListResponse;
import com.svi.tictactoewebservice.service.PlayerService;
import com.svi.tictactoewebservice.service.impl.PlayerServiceImpl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;


@Path("/player")
public class PlayerController {

    private final PlayerService playerService = new PlayerServiceImpl();

    // Retrieves all games participated in by a player
    @GET
    @Path("/{playerId}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId")
                              @NotBlank(message = "Player ID is required.")
                              @Pattern(
                                      regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                                      message = "Player ID must be a valid UUID."
                              )
                              String playerId) {

        try {
            GameListResponse response = playerService.getPlayerGames(playerId);

            if (response == null) {
                return Response.status(402).entity(new ErrorResponse("Record not found")).build();
            }

            return Response.ok(response).build();

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


    // Retrieves all rooms participated in by the specified player, including their games in each room
    @GET
    @Path("/{playerId}/rooms")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRooms(@PathParam("playerId")
                              @NotBlank(message = "Player ID is required.")
                              @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                                       message = "Player ID must be a valid UUID.")
                              String playerId) {

        try {
            RoomListResponse response = playerService.getPlayerRooms(playerId);

            if (response == null) {
                return Response.status(402).entity(new ErrorResponse("Record not found")).build();
            }

            return Response.ok(response).build();


        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();

        } catch (IOException e) {
            return Response.status(500).entity(new ErrorResponse("The server ran into an unexpected exception.")).build();
        }
    }






}