package org.wirvsvirushackathon.servcie;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.wirvsvirushackathon.configuration.environment.TwillioEnvironment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SMSService {

    @Inject
    private TwillioEnvironment twillioEnvironment;

    public void sendMessage(String messageString, String phoneNumber){
        Twilio.init(twillioEnvironment.getAccountSid(), twillioEnvironment.getAuthToken());

        Message message = Message
                .creator(new PhoneNumber(phoneNumber), // to
                        new PhoneNumber("+14158141829"), // from
                        messageString)
                .create();

        System.out.println(message.getSid());
    }

}
