package com.nataliajastrzebska.insidespy.trackContactDetails;

/**
 * Created by nataliajastrzebska on 17/02/16.
 */
public class Sms {
    private String body;
    private String date;

    public Sms(String body, String date) {
        this.body = body;
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.body;
    }
}
