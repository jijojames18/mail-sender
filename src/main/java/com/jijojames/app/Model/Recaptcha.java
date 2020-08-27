package com.jijojames.app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Recaptcha {
    @JsonProperty("success")
    private String status;

    @JsonProperty("challenge_ts")
    private String timeStamp;

    @JsonProperty("hostname")
    private String hostName;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public String getSuccess() {
        return status;
    }

    public Boolean isSuccess() {
        return status.equals("true");
    }
}