package com.svi.tictactoewebservice;

import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.service.GameService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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

            JsonArrayBuilder moveList = Json.createArrayBuilder();

            for (MoveRecord move : moves) {
                moveList.add(
                        Json.createObjectBuilder()
                                .add("gameid", move.getGameid())
                                .add("playerid", move.getPlayerid())
                                .add("symbol", move.getSymbol())
                                .add("location", move.getLocation())
                                .add("datesave", move.getDatesave())
                                .build()
                );
            }

            JsonObject responseBody = Json.createObjectBuilder()
                    .add("list", moveList.build())
                    .add("msg", "Records found")
                    .build();

            return Response.ok(responseBody).build();

        } catch (IOException e) {
            return Response.status(500)
                    .entity("{\"msg\":\"The server ran into an unexpected exception.\"}")
                    .build();
        }
    }
}