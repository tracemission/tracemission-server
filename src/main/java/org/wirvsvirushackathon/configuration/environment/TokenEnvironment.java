package org.wirvsvirushackathon.configuration.environment;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.wirvsvirushackathon.servcie.PersonService;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class TokenEnvironment {

    private static final Logger LOG = Logger.getLogger(PersonService.class);

    @ConfigProperty(name = "JWT_SECRET")
    private Optional<String> secret;

    public Optional<String> getSecret() {
        if (!secret.isPresent()) LOG.error("Private Key is not provided");
        return secret;
    }

}
