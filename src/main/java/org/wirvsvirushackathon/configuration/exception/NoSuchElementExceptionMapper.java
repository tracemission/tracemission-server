package org.wirvsvirushackathon.configuration.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class NoSuchElementExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<NoSuchElementException> {
    @Override
    public Response toResponse(NoSuchElementException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
