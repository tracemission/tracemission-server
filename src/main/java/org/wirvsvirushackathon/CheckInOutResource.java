package org.wirvsvirushackathon;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.CheckInOutData;
import org.wirvsvirushackathon.servcie.CheckInOutService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckInOutResource {

    @Inject
    private CheckInOutService CIOService;

    @POST
    @Path("/checkIn")
    public CheckInOutData checkIn(@Valid CheckInOutData data) {
        CIOService.checkIn(data);
        return data;
    }

    @POST
    @Path("/checkOut")
    public CheckInOutData checkOut(@Valid CheckInOutData data) {
        CIOService.checkOut(data);
        return data;
    }
}