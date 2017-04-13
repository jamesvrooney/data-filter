package com.sedex.test;

/**
 * Created by jamesvrooney on 13/04/17.
 */
public class LogEntry {
    long requestTimstamp;
    String countryCode;
    long responseTime;

    public LogEntry(long requestTimstamp, String countryCode, long responseTime) {
        this.requestTimstamp = requestTimstamp;
        this.countryCode = countryCode;
        this.responseTime = responseTime;
    }

    public long getRequestTimstamp() {
        return requestTimstamp;
    }

    public void setRequestTimstamp(long requestTimstamp) {
        this.requestTimstamp = requestTimstamp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
