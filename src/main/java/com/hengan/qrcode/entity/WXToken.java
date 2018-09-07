package com.hengan.qrcode.entity;

public class WXToken {
    public String accessToken;
    public boolean isEXpire;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isEXpire() {
        return isEXpire;
    }

    public void setEXpire(boolean EXpire) {
        isEXpire = EXpire;
    }
}
