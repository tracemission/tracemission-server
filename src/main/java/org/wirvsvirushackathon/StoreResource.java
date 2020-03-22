package org.wirvsvirushackathon;

import io.quarkus.security.AuthenticationFailedException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.Store;
import org.wirvsvirushackathon.servcie.StoreService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
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

    @POST
    @Path("/{id}/verify")
    public CompletionStage<Response> requestVerification(@PathParam UUID id) {
        return storeService.verifyStore(id).thenApply(person -> {
                    if (person == null) {
                        throw new WebApplicationException("Person with id of " + id.toString() + " does not exist.", 404);
                    }
                    return Response.accepted().build();
                }
        );
    }

    @POST
    @Path("/{id}/verify/{key}")
    public CompletionStage<Map<String, String>> checkVerification(@PathParam UUID id, @PathParam long key) {
        return storeService.checkVerification(id, key).thenApply(token -> {
                    if (token == null) {
                        throw new AuthenticationFailedException();
                    }
                    Map<String, String> tokenResponse = new HashMap<>();
                    tokenResponse.put("access_token", token);
                    return tokenResponse;
                }
        );
    }

}