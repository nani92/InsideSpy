package com.nataliajastrzebska.insidespy;


/**
 * Created by nataliajastrzebska on 08/02/16.
 */
public class Contact {
    String number;
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return number;
    }
}
