/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.deutsche.DeutscheTokenRenewalResponse;
import com.j2020.model.TokenRenewalResponse;
import com.j2020.service.TokenRequestRetrievalService;
import com.j2020.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class DeutscheTokenService implements TokenService {
    private TokenRequestRetrievalService tokenRetrieval;
    private String currentToken;
    private LocalDateTime lastRefreshDayTime;
    private long tokenValidFor;
    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @Value("${deutscheTokenRenewal.deutTokenRenewalUri}")
    private String deutTokenRenewalUri;

    @Value("${deutscheTokenRenewal.deutClientId}")
    private String deutClientId;

    @Value("${deutscheTokenRenewal.deutClientSecret}")
    private String deutClientSecret;

    @Value("${deutscheTokenRenewal.OAuthToken}")
    private String oAuthToken;

    public DeutscheTokenService(TokenRequestRetrievalService tokenRetrieval) {
        this.tokenRetrieval = tokenRetrieval;
    }

    public String getToken() {
        if (lastRefreshDayTime.plusSeconds(tokenValidFor).isBefore(LocalDateTime.now())) {
            refreshToken();
        }
        return currentToken;
    }

    public void refreshToken() {
        JavaType type = new ObjectMapper().getTypeFactory().constructType(DeutscheTokenRenewalResponse.class);
        TokenRenewalResponse renewalResponse = tokenRetrieval.retrieveToken(params, deutTokenRenewalUri, type);

        currentToken = renewalResponse.getAccessToken();
        tokenValidFor = renewalResponse.getSecondsUntilExpiring();
        lastRefreshDayTime = LocalDateTime.now();
    }

    @PostConstruct
    private void init() {
        params.add("client_id", deutClientId);
        params.add("client_id", deutClientId);
        params.add("client_secret", deutClientSecret);
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", oAuthToken);

        refreshToken();
    }
}
