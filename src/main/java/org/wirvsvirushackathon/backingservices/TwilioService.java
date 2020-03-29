package org.wirvsvirushackathon.backingservices;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.jaxrs.HeaderParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.wirvsvirushackathon.model.TwilioSMS;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/2010-04-01")
@RegisterRestClient(baseUri="https://api.twilio.com")
public interface TwilioService {

    @POST
    @Path("/Accounts/{accountSid}/Messages.json")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    Response sendSMS(@HeaderParam("Authorization") String auth, @PathParam String accountSid, @Form TwilioSMS twilioSMS);

}
