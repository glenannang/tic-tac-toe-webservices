package com.svi.jakarta.hello;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

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

        return Response.status(501)
                .entity("{\"msg\":\"Game retrieval not implemented\"}")
                .build();
    }
}