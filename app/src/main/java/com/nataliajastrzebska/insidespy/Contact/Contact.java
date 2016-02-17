package com.nataliajastrzebska.insidespy.contact;


/**
 * Created by nataliajastrzebska on 08/02/16.
 */
public class Contact {
    String number;
    String name;
    long id;
    Type type;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    public enum Type {
        TRACK,
        SPY,
        UNKOWN;


        @Override
        public String toString() {
            switch (this) {
                case TRACK:
                    return "track";
                case SPY:
                    return "spy";
            }
            return "unkown";
        }

        public static Type fromString(String typeString) {
            if (typeString.equals("track"))
                return TRACK;
            if (typeString.equals("spy"))
                return SPY;
            return UNKOWN;
        }
    }
}
