/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.deutsche;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.TokenRenewalResponse;

public class DeutscheTokenRenewalResponse implements TokenRenewalResponse {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "expires_in")
    private int secondsUntilExpiring;

    @JsonProperty(value = "scope")
    private String allowedOperations;

    @JsonProperty(value = "id_token")
    private String identityToken;

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public long getSecondsUntilExpiring() {
        return secondsUntilExpiring;
    }

    public void setSecondsUntilExpiring(int secondsUntilExpiring) {
        this.secondsUntilExpiring = secondsUntilExpiring;
    }

    public String getAllowedOperations() {
        return allowedOperations;
    }

    public void setAllowedOperations(String allowedOperations) {
        this.allowedOperations = allowedOperations;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String identityToken) {
        this.identityToken = identityToken;
    }
}
