package org.wirvsvirushackathon;

import org.wirvsvirushackathon.model.Visit;
import org.wirvsvirushackathon.service.VisitService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("/visit")
@Consumes(MediaType.APPLICATION_JSON)
public class VisitResource {

    @Inject
    private VisitService visitService;

    @POST
    public CompletionStage<Visit> checkIn(@Valid Visit data) {
        return visitService.checkIn(data);
    }

    @POST
    @Path("/checkout")
    public CompletionStage<Visit> checkOut(@Valid Visit data) {
        return visitService.checkOut(data);
    }


}