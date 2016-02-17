package com.nataliajastrzebska.insidespy.trackContactDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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

    public String getFormatDate() {
        Date date = new Date();
        date.setTime(Long.parseLong(this.date));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        return dateFormat.format(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.body;
    }
}
