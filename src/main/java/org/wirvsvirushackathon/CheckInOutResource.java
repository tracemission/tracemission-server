package org.wirvsvirushackathon;

import org.wirvsvirushackathon.model.CheckInOutData;
import org.wirvsvirushackathon.servcie.CheckInOutService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/check")
@Consumes(MediaType.APPLICATION_JSON)
public class CheckInOutResource {

    @Inject
    private CheckInOutService CIOService;

    @POST
    @Path("/in")
    public Response checkIn(@Valid CheckInOutData data) {
        CIOService.checkIn(data);
        return Response.accepted().build();
    }

    @POST
    @Path("/out")
    public Response checkOut(@Valid CheckInOutData data) {
        CIOService.checkOut(data);
        return Response.accepted().build();
    }


}