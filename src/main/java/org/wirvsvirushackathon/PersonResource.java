package org.wirvsvirushackathon;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.servcie.PersonService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    private PersonService personService;

    @GET
    @Path("/{id}")
    public Person get(@PathParam long id) {
        final Person person = personService.getPersonById(id);
        if(person == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        return person;
    }

    @POST
    public Person add(@Valid Person person) {
        personService.registerPerson(person);
        return person;
    }

}