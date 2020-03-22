package org.wirvsvirushackathon.configuration.exception;

import io.quarkus.security.AuthenticationFailedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

@Provider
public class AuthenticationExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<AuthenticationFailedException> {
    @Override
    public Response toResponse(AuthenticationFailedException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
