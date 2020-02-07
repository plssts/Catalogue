/**
 * @author Paulius Staisiunas
 */

package com.j2020.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class RevolutTokenRenewalResponse implements TokenRenewalResponse {
    @JsonProperty(value = )
    private String accessToken;
    private String tokenType;
    private int secondsUntilExpiring;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getSecondsUntilExpiring() {
        return secondsUntilExpiring;
    }

    public void setSecondsUntilExpiring(int secondsUntilExpiring) {
        this.secondsUntilExpiring = secondsUntilExpiring;
    }
}
