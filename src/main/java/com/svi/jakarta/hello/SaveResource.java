package com.svi.jakarta.hello;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Path("save")
public class SaveResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response save(MoveRecord record) {

        try{
            File recordsFolder = new File("records");

            if(!recordsFolder.exists()){
                recordsFolder.mkdir();
            }

            File playerFile = new File(
                    recordsFolder,
                    record.getPlayerid() + ".txt"
            );

            if (!playerFile.exists()) {
                playerFile.createNewFile();
            }
            try (FileWriter writer = new FileWriter(playerFile, true)) {
                writer.write(record.getGameid());
                writer.write(System.lineSeparator());
            }

            return Response
                    .status(200)
                    .entity("{\"msg\":\"Record saved.\"}")
                    .build();

        } catch(IOException e){

            return Response
                    .status(401)
                    .entity("{\"msg\":\"Record could not be saved\"}")
                    .build();

        }

    }

}
