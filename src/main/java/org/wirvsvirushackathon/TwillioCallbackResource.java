package org.wirvsvirushackathon;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/callback/sms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TwillioCallbackResource {

    @POST
    public Response add() {
        return Response.ok().build();
    }

}