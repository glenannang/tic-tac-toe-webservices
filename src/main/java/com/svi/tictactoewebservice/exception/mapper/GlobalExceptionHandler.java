package com.svi.tictactoewebservice.exception.mapper;

import com.svi.tictactoewebservice.dto.response.ErrorResponse;
import com.svi.tictactoewebservice.exception.BadRequestException;
import com.svi.tictactoewebservice.exception.InternalServerException;
import com.svi.tictactoewebservice.exception.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof BadRequestException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(exception.getMessage())).build();
        }

        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse(exception.getMessage())).build();
        }

        if (exception instanceof InternalServerException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(exception.getMessage())).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("The server ran into an unexpected exception.")).build();
    }
}