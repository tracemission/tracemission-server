package org.wirvsvirushackathon;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.servcie.PersonService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public CompletionStage<Person> add(@Valid Person person) {
        return personService.registerPerson(person);
    }

}