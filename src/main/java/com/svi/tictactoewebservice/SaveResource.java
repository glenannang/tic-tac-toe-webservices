package com.svi.tictactoewebservice;

import com.svi.tictactoewebservice.model.MoveRecord;
import com.svi.tictactoewebservice.service.GameService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;

@Path("save")
public class SaveResource {
    private final GameService gameService = new GameService();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response save(MoveRecord record) {

        try{
              gameService.saveMove(record);

            return Response
                    .status(200)
                    .entity("{\"msg\":\"Record saved.\"}")
                    .build();

        } catch(IOException e){
            return Response.status(401).entity("{\"msg\":\"Record could not be saved\"}").build();

        } catch (Exception e){
            return Response.status(500).entity("{\"msg\":\"Record could not be saved\"}").build();
        }

    }

}
