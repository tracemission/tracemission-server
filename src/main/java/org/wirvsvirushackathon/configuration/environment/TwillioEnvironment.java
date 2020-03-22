package org.wirvsvirushackathon.configuration.environment;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.wirvsvirushackathon.servcie.SMSService;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class TwillioEnvironment {

    private static final Logger LOG = Logger.getLogger(TwillioEnvironment.class);

    @ConfigProperty(name = "TWILLIO.ACCOUNT_SID")
    private Optional<String> accountSid;

    @ConfigProperty(name = "TWILLIO.AUTH_TOKEN")
    private Optional<String> authToken;

    @ConfigProperty(name = "TWILLIO.PHONE_NUMBER")
    private Optional<String> phoneNumber;

    public Optional<String> getAccountSid() {
        if(!accountSid.isPresent()) LOG.error("Account SID for Twillio is not provided");
        return accountSid;
    }

    public Optional<String> getAuthToken() {
        if(!authToken.isPresent()) LOG.error("Auth token for Twillio is not provided");
        return authToken;
    }

    public Optional<String> getPhoneNumber() {
        if(!phoneNumber.isPresent()) LOG.error("Phone number for Twillio is not provided");
        return phoneNumber;
    }
}
