package com.jijojames.app.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Recaptcha {
    @JsonProperty("success")
    private String success;

    @JsonProperty("challenge_ts")
    private String timeStamp;

    @JsonProperty("hostname")
    private String hostName;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public String getChallengeTimestamp() {
        return timeStamp;
    }

    public String getSuccess() {
        return success;
    }

    public Boolean isSuccess() {
        return success.equals("success");
    }
}
