package com.svi.tictactoewebservice;

import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.dto.response.GameDetailsResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;


@Path("game")

public class GetGameResource {
    private final GameService gameService = new GameService();

    @GET
    @Path("{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("gameId") String gameId) {

        try {
            List<MoveRecord> moves = gameService.getGameDetails(gameId);

            if (moves == null) {
                return Response.status(402)
                        .entity("{\"msg\":\"Record not found\"}")
                        .build();
            }

            GameDetailsResponse responseBody = new GameDetailsResponse(moves, "Records found");
            return Response.ok(responseBody).build();

        } catch (IOException e) {
            return Response.status(500)
                    .entity("{\"msg\":\"The server ran into an unexpected exception.\"}")
                    .build();
        }
    }
}