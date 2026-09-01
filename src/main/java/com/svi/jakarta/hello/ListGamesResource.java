package com.svi.jakarta.hello;

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

@Path("list-games")
public class ListGamesResource {
    @GET
    @Path("{playerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId") String playerId) {

        File recordsFolder = new File("records");
        File playerFile = new File(recordsFolder, playerId + ".txt");

        if (!playerFile.exists()) {
            return Response.status(402).entity("{\"msg\":\"Record not found\"}").build();
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(playerFile))){
            JsonArrayBuilder gameList = Json.createArrayBuilder();
            String line;


            while((line = reader.readLine()) != null){
                if (line.trim().isEmpty()) {continue;}
                gameList.add(Json.createObjectBuilder().add("id", line.trim()).build());
            }

            JsonObject responseBody = Json.createObjectBuilder()
                            .add("list", gameList.build())
                            .add("msg", "Records found")
                            .build();

            return Response.ok(responseBody).build();

        }catch(IOException e){
            return Response.status(500).entity("{\"msg\":\"The server ran into an unexpected exception.\"}").build();
        }

    }

}
