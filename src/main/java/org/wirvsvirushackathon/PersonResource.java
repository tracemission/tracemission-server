package org.wirvsvirushackathon;

import io.quarkus.security.AuthenticationFailedException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.servcie.PersonService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionStage;


@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    private PersonService personService;

    @GET
    @Path("/{id}")
    public CompletionStage<Person> get(@PathParam UUID id) {
        return personService.getPersonById(id).thenApply(person -> {
                    if (person == null) {
                        throw new WebApplicationException("Person with id of " + id.toString() + " does not exist.", 404);
                    }
                    return person;
                }
        );
    }

    @POST
    @Path("/{id}/infected")
    public CompletionStage<Person> reportInfected(@PathParam UUID id) {
        return personService.reportInfected(id).thenApply(person -> {
                    if (person == null) {
                        throw new WebApplicationException("Person with id of " + id.toString() + " does not exist.", 404);
                    }
                    return person;
                }
        );
    }

    @GET
    @Path("/{id}/verify")
    public CompletionStage<Response> requestVerification(@PathParam UUID id) {
        return personService.verifyPerson(id).thenApply(person -> {
                    if (person == null) {
                        throw new WebApplicationException("Person with id of " + id.toString() + " does not exist.", 404);
                    }
                    return Response.accepted().build();
                }
        );
    }

    @GET
    @Path("/{id}/verify/{key}")
    public CompletionStage<Map<String, String>> checkVerification(@PathParam UUID id, @PathParam long key) {
        return personService.checkVerification(id, key).thenApply(token -> {
                    if (token == null) {
                        throw new AuthenticationFailedException();
                    }
                    Map<String, String> tokenResponse = new HashMap<>();
                    tokenResponse.put("access_token", token);
                    return tokenResponse;
                }
        );
    }

    @POST
    public CompletionStage<Person> add(@Valid Person person) {
        return personService.registerPerson(person, false);
    }

    @POST
    @Path("/verified")
    public CompletionStage<Person> addVerified(@Valid Person person) {
        return personService.registerPerson(person, true);
    }

}