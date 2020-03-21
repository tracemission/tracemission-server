package org.wirvsvirushackathon;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.Store;
import org.wirvsvirushackathon.servcie.StoreService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.concurrent.CompletionStage;


@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreResource {

    @Inject
    private StoreService storeService;

    @GET
    @Path("/{id}")
    public CompletionStage<Store> get(@PathParam UUID id) {
        return storeService.getStoreById(id).thenApply(store -> {
                    if (store == null) {
                        throw new WebApplicationException("Person with id of " + id.toString() + " does not exist.", 404);
                    }
                    return store;
                }
        );
    }

    @POST
    public CompletionStage<Store> add(@Valid Store store) {
        return storeService.registerStore(store);
    }

}