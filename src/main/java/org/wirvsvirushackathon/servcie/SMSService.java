package org.wirvsvirushackathon.servcie;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.jboss.logging.Logger;
import org.wirvsvirushackathon.configuration.environment.TwillioEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.apache.http.util.TextUtils.isBlank;

@Singleton
public class SMSService {

    private static final Logger LOG = Logger.getLogger(SMSService.class);

    @Inject
    private TwillioEnvironment twillioEnvironment;

    public void sendMessage(String messageString, String phoneNumber) {

        if (!twillioEnvironment.getAccountSid().isPresent() || !twillioEnvironment.getPhoneNumber().isPresent() || !twillioEnvironment.getAuthToken().isPresent()) {
            return;
        }

        Twilio.init(twillioEnvironment.getAccountSid().get(), twillioEnvironment.getAuthToken().get());

        Message message = Message
                .creator(new PhoneNumber(phoneNumber), // to
                        new PhoneNumber(twillioEnvironment.getPhoneNumber().get()), // from
                        messageString)
                .create();

        LOG.debug("Twillio message: {}" + message.getSid());
    }

}
