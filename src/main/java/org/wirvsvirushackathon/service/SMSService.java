package org.wirvsvirushackathon.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.wirvsvirushackathon.backingservices.TwilioService;
import org.wirvsvirushackathon.configuration.environment.TwillioEnvironment;
import org.wirvsvirushackathon.model.TwilioSMS;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.util.Base64;

@Singleton
public class SMSService {

    @Inject
    private TwillioEnvironment twillioEnvironment;

    @Inject
    @RestClient
    private TwilioService twilioService;

    private static final Logger LOG = Logger.getLogger(SMSService.class);


    public Response sendMessage(String messageString, String phoneNumber) {

        if (!twillioEnvironment.getAccountSid().isPresent() || !twillioEnvironment.getPhoneNumber().isPresent() || !twillioEnvironment.getAuthToken().isPresent()) {
            return null;
        }

        String auth = lookupAuth(twillioEnvironment.getAccountSid().get(), twillioEnvironment.getAuthToken().get());

        TwilioSMS twilioSMS = new TwilioSMS(phoneNumber, twillioEnvironment.getPhoneNumber().get(), messageString);
        LOG.info("******************************************************Send sms with auth= "+ auth);
        return twilioService.sendSMS(auth, twillioEnvironment.getAccountSid().get(), twilioSMS);

    }

    private String lookupAuth(String accountSid, String token) {
        return "Basic " +
                Base64.getEncoder().encodeToString((accountSid+":"+token).getBytes());
    }
}
