package org.wirvsvirushackathon.configuration.environment;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;

@Singleton
public class TwillioEnvironment {

    @ConfigProperty(name = "TWILLIO.ACCOUNT_SID", defaultValue = "")
    private String accountSid;

    @ConfigProperty(name = "TWILLIO.SERVICE_SID", defaultValue = "")
    private String serviceSid;

    @ConfigProperty(name = "TWILLIO.AUTH_TOKEN", defaultValue = "")
    private String authToken;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }
}
