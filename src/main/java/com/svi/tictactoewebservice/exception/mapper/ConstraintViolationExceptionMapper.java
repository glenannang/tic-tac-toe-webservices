package com.svi.tictactoewebservice.exception.mapper;

import com.svi.tictactoewebservice.dto.response.ErrorResponse;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        String message = exception.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(message))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}