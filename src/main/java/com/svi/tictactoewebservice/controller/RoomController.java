package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.dto.response.*;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.service.RoomService;
import com.svi.tictactoewebservice.service.impl.GameServiceImpl;
import com.svi.tictactoewebservice.service.impl.RoomServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
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


    // Creates a new room record.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(@Valid RoomRequest request) {
        try {
            ApiResponse response = roomService.createRoom(request);
            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create room.")).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    // Creates a new game and associates it with the specified room
    @POST
    @Path("/{roomCode}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGameRecord(@PathParam("roomCode")
                                     @NotBlank(message = "Room Code is required.")
                                     @Pattern(regexp = "^[A-F0-9]{6}$",
                                             message = "Room code must be a valid 6-character code.")
                                     String roomCode) {

        try {
            GameIdResponse gameIdResponse = gameService.createGameRecord(roomCode);

            if (gameIdResponse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Room not found.")).build();
            }

            return Response.status(Response.Status.CREATED).entity(gameIdResponse).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create game record.")).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
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

        try {
            RoomResponse roomResponse = roomService.getRoom(roomCode);

            if(roomResponse == null){
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Room not found")).build();
            }
            return Response.ok(roomResponse).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to retrieve room.")).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }

    }

}