package com.svi.tictactoewebservice;
import com.svi.tictactoewebservice.service.GameService;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Path("list-games")
public class ListGamesResource {
    private final GameService gameService = new GameService();

    @GET
    @Path("{playerId}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listGames(@PathParam("playerId") String playerId) {

        try {
            List<String> games = gameService.getPlayerGames(playerId);

            if (games == null) {
                return Response.status(402).entity("{\"msg\":\"Record not found\"}").build();
            }

            JsonArrayBuilder gameList = Json.createArrayBuilder();

            for (String gameId : games) {
                gameList.add(Json.createObjectBuilder().add("id", gameId).build());
            }

            JsonObject responseBody = Json.createObjectBuilder()
                    .add("list", gameList.build())
                    .add("msg", "Records found")
                    .build();

            return Response.ok(responseBody).build();

        } catch(IOException e){
            return Response.status(500).entity("{\"msg\":\"The server ran into an unexpected exception.\"}").build();
        }

    }

}
