package com.nataliajastrzebska.insidespy.helpers;

/**
 * Created by nataliajastrzebska on 14/02/16.
 */
public class BtsLocation {
    private int cid;
    private int lac;
    private String mcc;
    private String mnc;

    public BtsLocation(int cid, int lac, String mcc, String mnc) {
        this.cid = cid;
        this.lac = lac;
        this.mcc = mcc;
        this.mnc = mnc;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

}
