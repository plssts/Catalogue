/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.TokenRenewalResponse;

public class RevolutTokenRenewalResponse implements TokenRenewalResponse {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "expires_in")
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

    public long getSecondsUntilExpiring() {
        return secondsUntilExpiring;
    }

    public void setSecondsUntilExpiring(int secondsUntilExpiring) {
        this.secondsUntilExpiring = secondsUntilExpiring;
    }
}
