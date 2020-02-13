/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.revolut;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.TokenRenewalResponse;
import com.j2020.model.revolut.RevolutTokenRenewalResponse;
import com.j2020.service.TokenRequestRetrievalService;
import com.j2020.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class RevolutTokenService implements TokenService {
    private TokenRequestRetrievalService tokenRetrieval;
    private String currentToken;
    private LocalDateTime lastRefreshDayTime;
    private long tokenValidFor;
    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @Value("${revolutTokenRenewal.revoTokenRenewalUri}")
    private String revoTokenRenewalUri;

    @Value("${revolutTokenRenewal.revoRefreshToken}")
    private String revoRefreshToken;

    @Value("${revolutTokenRenewal.revoClientId}")
    private String revoClientId;

    @Value("${revolutTokenRenewal.clAssertType}")
    private String clAssertType;

    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    public RevolutTokenService(TokenRequestRetrievalService tokenRetrieval) {
        this.tokenRetrieval = tokenRetrieval;
    }

    public String getToken() {
        if (lastRefreshDayTime.plusSeconds(tokenValidFor).isBefore(LocalDateTime.now())) {
            refreshToken();
        }
        System.out.println("Use this:\n" + currentToken); // FIXME remove after debugging
        return currentToken;
    }

    public void refreshToken() {
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutTokenRenewalResponse.class);
        TokenRenewalResponse renewalResponse = tokenRetrieval.retrieveToken(params, revoTokenRenewalUri, type);

        currentToken = renewalResponse.getAccessToken();
        tokenValidFor = renewalResponse.getSecondsUntilExpiring();
        lastRefreshDayTime = LocalDateTime.now();
    }

    @PostConstruct
    private void init() {
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", revoRefreshToken);
        params.add("client_id", revoClientId);
        params.add("client_assertion_type", clAssertType);
        params.add("client_assertion", OAuthJWT);
        refreshToken();
    }
}
