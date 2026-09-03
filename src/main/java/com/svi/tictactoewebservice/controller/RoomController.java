package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.dto.response.*;
import com.svi.tictactoewebservice.service.impl.GameServiceImpl;
import com.svi.tictactoewebservice.service.impl.RoomServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;


@Path("")
public class RoomController {

    private final GameServiceImpl gameService = new GameServiceImpl();
    private final RoomServiceImpl roomService = new RoomServiceImpl();

    @POST
    @Path("/createRoom")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(RoomRequest request) {
        try {
            RoomResponse roomResponse = roomService.createRoom(request);
            return Response.status(Response.Status.CREATED).entity(roomResponse).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create room.")).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @POST
    @Path("/rooms/{roomCode}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGameRecord(@PathParam("roomCode") String roomCode) {

        try {
            GameIdResponse gameIdResponse = gameService.createGameRecord(roomCode);

            if (gameIdResponse == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Room not found.")).build();
            }

            return Response.status(Response.Status.CREATED).entity(gameIdResponse).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create game record.")).build();
        }
    }

    @GET
    @Path("getRoom/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("roomId") String roomId){
        try {
            RoomResponse roomResponse = roomService.getRoom(roomId);

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