package org.wirvsvirushackathon.configuration.environment;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;

@Singleton
public class TwillioEnvironment {

    @ConfigProperty(name = "TWILLIO.ACCOUNT_SID")
    private String accountSid;

    @ConfigProperty(name = "TWILLIO.AUTH_TOKEN")
    private String authToken;

    @ConfigProperty(name = "TWILLIO.PHONE_NUMBER")
    private String phoneNumber;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
