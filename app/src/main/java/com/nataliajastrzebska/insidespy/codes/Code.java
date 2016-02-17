package com.nataliajastrzebska.insidespy.codes;

/**
 * Created by nataliajastrzebska on 31/01/16.
 */
public enum Code {
    GET,
    GETGPS,
    UNKNOWN;

    @Override
    public String toString() {
        switch (this) {
            case GET:
                return "get()";
            case GETGPS:
                return "getgps()";
        }

        return "";
    }

    public static Code fromString(String string){
        if(string.equals("get()"))
            return GET;
        if(string.equals("getgps()"))
            return GETGPS;
        return UNKNOWN;
    }
}