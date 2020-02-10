/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.j2020.model.DeutscheTokenRenewalResponse;
import com.j2020.model.RevolutTokenRenewalResponse;
import com.j2020.model.TokenFetchException;
import com.j2020.model.TokenRenewalResponse;
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
        if (lastRefreshDayTime.plusSeconds(tokenValidFor).isBefore(LocalDateTime.now())){
            refreshToken();
        }
        return currentToken;
    }

    public void refreshToken() throws TokenFetchException {
        TokenRenewalResponse renewalResponse = tokenRetrieval.retrieveToken(params,
                revoTokenRenewalUri,
                new TypeReference<DeutscheTokenRenewalResponse>(){});

        currentToken = renewalResponse.getAccessToken();
        tokenValidFor = renewalResponse.getSecondsUntilExpiring();

        lastRefreshDayTime = LocalDateTime.now();
    }

    @PostConstruct
    private void init() {
        try {
            System.out.println("post construct of revolut token");
            params.add("grant_type", "refresh_token");
            params.add("refresh_token", revoRefreshToken);
            params.add("client_id", revoClientId);
            params.add("client_assertion_type", clAssertType);
            params.add("client_assertion", OAuthJWT);
            refreshToken();

        } catch (TokenFetchException ex) {
            System.out.println("post construct failed");
            ex.printStackTrace();
            throw new RuntimeException("Token negotiation failed. Application clearance might have expired");
        }
    }
}
