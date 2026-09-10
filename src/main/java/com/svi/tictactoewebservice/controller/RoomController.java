package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.response.*;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.service.RoomService;
import com.svi.tictactoewebservice.service.impl.GameServiceImpl;
import com.svi.tictactoewebservice.service.impl.RoomServiceImpl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;

@Path("/room")
public class RoomController {

    private final GameService gameService = new GameServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();


    // Creates a new game and associates it with the specified room
    @POST
    @Path("/{roomCode}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGameRecord(@PathParam("roomCode")
                                     @NotBlank(message = "Room Code is required.")
                                     @Pattern(regexp = "^[A-F0-9]{6}$",
                                             message = "Room code must be a valid 6-character code.")
                                     String roomCode) {

            GameIdResponse gameIdResponse = gameService.createGameRecord(roomCode);
            return Response.status(Response.Status.CREATED).entity(gameIdResponse).build();

    }

    // Retrieves the specified room and all games created within it
    @GET
    @Path("/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("roomCode")
                                @NotBlank(message = "Room Code is required.")
                                @Pattern(regexp = "^[A-F0-9]{6}$",
                                        message = "Room code must be a valid 6-character code.")
                                String roomCode){

            RoomResponse roomResponse = roomService.getRoom(roomCode);
            return Response.ok(roomResponse).build();

    }

}