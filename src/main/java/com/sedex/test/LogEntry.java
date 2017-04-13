package com.sedex.test;

/**
 * Created by jamesvrooney on 13/04/17.
 */
public class LogEntry {
    long requestTimstamp;
    String countryCode;
    long response_time;

    public LogEntry(long requestTimstamp, String countryCode, long response_time) {
        this.requestTimstamp = requestTimstamp;
        this.countryCode = countryCode;
        this.response_time = response_time;
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

    public long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(long response_time) {
        this.response_time = response_time;
    }
}
