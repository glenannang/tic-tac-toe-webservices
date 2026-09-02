package com.svi.tictactoewebservice.controller;

import com.svi.tictactoewebservice.dto.request.MoveRequest;
import com.svi.tictactoewebservice.dto.request.RoomRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.dto.response.ErrorResponse;
import com.svi.tictactoewebservice.dto.response.GameListResponse;
import com.svi.tictactoewebservice.model.Room;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.service.RoomService;
import com.svi.tictactoewebservice.dto.response.GameDetailsResponse;
import com.svi.tictactoewebservice.model.MoveRecord;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Path("")
public class GameController {

    private final GameService gameService = new GameService();
    private final RoomService roomService = new RoomService();

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(MoveRequest request) {

        try {
            gameService.saveMove(request);
            return Response.ok(new ApiResponse("Record saved.")).build();

        }
        catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
        catch (IOException e) {
            return Response.status(401).entity(new ErrorResponse("Record could not be saved")).build();

        } catch (Exception e) {
            return Response.status(500).entity(new ErrorResponse("Record could not be saved")).build();
        }
    }


    @GET
    @Path("list-games/{playerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId") String playerId) {

        try {
            List<String> games = gameService.getPlayerGames(playerId);

            if (games == null) {
                return Response.status(402).entity(new ErrorResponse("Record not found")).build();
            }

            List<GameListResponse.GameId> gameList = new ArrayList<>();

            for (String gameId : games) {
                gameList.add(new GameListResponse.GameId(gameId));
            }

            return Response.ok(new GameListResponse(gameList, "Records found")).build();

        } catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();

        } catch (IOException e) {
            return Response.status(500).entity(new ErrorResponse("The server ran into an unexpected exception.")).build();
        }
    }


    @GET
    @Path("game/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("gameId") String gameId) {

        try {
            List<MoveRecord> moves = gameService.getGameDetails(gameId);

            if (moves == null) {
                return Response.status(402).entity(new ErrorResponse("Record not found")).build();
            }

            return Response.ok(new GameDetailsResponse(moves, "Records found")).build();

        } catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();

        } catch (IOException e) {
            return Response.status(500).entity(new ErrorResponse("The server ran into an unexpected exception.")).build();
        }
    }

    @POST
    @Path("/createRoom")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(RoomRequest request) {
        try {
            Room room = roomService.createRoom(request);
            return Response.status(Response.Status.CREATED).entity(room).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create room.")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }
    }


    @GET
    @Path("getRoom/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("roomId") String roomId){
        try {
            Room room = roomService.getRoom(roomId);
            return Response.status(Response.Status.OK).entity(room).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("Failed to create room.")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }

    }


}