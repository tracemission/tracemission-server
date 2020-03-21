package org.wirvsvirushackathon;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.Store;
import org.wirvsvirushackathon.servcie.StoreService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreResource {

    @Inject
    private StoreService storeService;

    @GET
    @Path("/{id}")
    public Store get(@PathParam long id) {
        final Store store = storeService.getStoreById(id);
        if(store == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        return store;
    }

    @POST
    public Store add(@Valid Store store) {
        storeService.registerStore(store);
        return store;
    }

}