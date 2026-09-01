package com.svi.tictactoewebservice;
import com.svi.tictactoewebservice.service.GameService;
import com.svi.tictactoewebservice.dto.response.GameListResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
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
            List<GameListResponse.GameId> gameList = new ArrayList<>();

            for (String gameId : games) {
                gameList.add(new GameListResponse.GameId(gameId));
            }

            GameListResponse responseBody =
                    new GameListResponse(gameList, "Records found");

            return Response.ok(responseBody).build();


        } catch(IOException e){
            return Response.status(500).entity("{\"msg\":\"The server ran into an unexpected exception.\"}").build();
        }

    }

}
