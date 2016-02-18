package com.nataliajastrzebska.insidespy.codes;

/**
 * Created by nataliajastrzebska on 31/01/16.
 */
public enum Code {
    GET,
    GETGPS,
    VIBRATE,
    UNKNOWN;

    final static String CODE_GET = "get()";
    final static String CODE_GETGPS = "getgps()";
    final static String CODE_VIBRATE = "vibrate()";

    @Override
    public String toString() {
        switch (this) {
            case GET:
                return CODE_GET;
            case GETGPS:
                return CODE_GETGPS;
            case VIBRATE:
                return CODE_VIBRATE;
        }

        return "";
    }

    public static Code fromString(String string) {
        if (string.equals(CODE_GET))
            return GET;
        if (string.equals(CODE_GETGPS))
            return GETGPS;
        if (string.equals(CODE_VIBRATE))
            return VIBRATE;
        return UNKNOWN;
    }
}