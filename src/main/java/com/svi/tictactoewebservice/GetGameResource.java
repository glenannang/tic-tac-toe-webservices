package com.svi.tictactoewebservice;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Path("game")

public class GetGameResource {

    @GET
    @Path("{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("gameId") String gameId) {

        File recordsFolder = new File("records");
        File gameFile = new File(recordsFolder, gameId + ".txt");

        if (!gameFile.exists()) {
            return Response.status(402)
                    .entity("{\"msg\":\"Record not found\"}")
                    .build();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(gameFile))) {

            JsonArrayBuilder moveList = Json.createArrayBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",", 5);

                if (fields.length != 5) {
                    return Response.status(500)
                            .entity("{\"msg\":\"The server ran into an unexpected exception.\"}")
                            .build();
                }

                moveList.add(Json.createObjectBuilder()
                        .add("gameid", fields[0].trim())
                        .add("playerid", fields[1].trim())
                        .add("symbol", fields[2].trim())
                        .add("location", fields[3].trim())
                        .add("datesave", fields[4].trim())
                        .build());
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