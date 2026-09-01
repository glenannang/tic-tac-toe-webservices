package com.svi.jakarta.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;


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


        return Response.status(200).entity("{\"msg\":\"Records found\"}").build();
    }

}
