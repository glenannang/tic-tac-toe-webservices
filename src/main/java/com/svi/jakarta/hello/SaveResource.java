package com.svi.jakarta.hello;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("save")
public class SaveResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response save(MoveRecord record) {
        return Response
                .status(200)
                .entity("{\"msg\":\"Record saved.\"}")
                .build();
    }



}
