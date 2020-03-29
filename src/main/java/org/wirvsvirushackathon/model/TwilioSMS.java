package org.wirvsvirushackathon.model;

import javax.ws.rs.FormParam;

public class TwilioSMS {

    @FormParam("To")
    private String to;
    @FormParam("From")
    private String from;
    @FormParam("Body")
    private String body;

    public TwilioSMS() {
    }

    public TwilioSMS(String to, String from, String body) {
        this.to = to;
        this.from = from;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return body;
    }

    public void setMessage(String body) {
        this.body = body;
    }
}
