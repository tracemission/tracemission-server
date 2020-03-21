package org.wirvsvirushackathon.servcie;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.jboss.logging.Logger;
import org.wirvsvirushackathon.configuration.environment.TwillioEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SMSService {

    private static final Logger LOG = Logger.getLogger(SMSService.class);

    @Inject
    private TwillioEnvironment twillioEnvironment;

    public void sendMessage(String messageString, String phoneNumber){

        if (twillioEnvironment.getAccountSid() == null || twillioEnvironment.getPhoneNumber() == null || twillioEnvironment.getAuthToken() == null) {
            return;
        }

        Twilio.init(twillioEnvironment.getAccountSid(), twillioEnvironment.getAuthToken());

        Message message = Message
                .creator(new PhoneNumber(phoneNumber), // to
                        new PhoneNumber(twillioEnvironment.getPhoneNumber()), // from
                        messageString)
                .create();

        LOG.debug("Twillio message: {}" + message.getSid());
    }

}
