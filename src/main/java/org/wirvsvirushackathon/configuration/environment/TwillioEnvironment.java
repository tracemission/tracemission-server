package org.wirvsvirushackathon.configuration.environment;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.wirvsvirushackathon.servcie.SMSService;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class TwillioEnvironment {


    @ConfigProperty(name = "TWILLIO.ACCOUNT_SID")
    private Optional<String> accountSid;

    @ConfigProperty(name = "TWILLIO.AUTH_TOKEN")
    private Optional<String> authToken;

    @ConfigProperty(name = "TWILLIO.PHONE_NUMBER")
    private Optional<String> phoneNumber;

    public Optional<String> getAccountSid() {
        return accountSid;
    }

    public Optional<String> getAuthToken() {
        return authToken;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }
}
