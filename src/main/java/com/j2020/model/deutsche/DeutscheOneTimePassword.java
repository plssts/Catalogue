package com.j2020.model.deutsche;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeutscheOneTimePassword {
    @JsonProperty(value = "otp")
    private String oneTimePassword;

    public DeutscheOneTimePassword() {
    }

    public DeutscheOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }
}
