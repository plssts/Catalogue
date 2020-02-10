/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.DeutscheTokenRenewalResponse;
import com.j2020.model.TokenFetchException;
import com.j2020.model.TokenRenewalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

    public String getToken() throws TokenFetchException {
        if (lastRefreshDayTime.plusSeconds(tokenValidFor).isBefore(LocalDateTime.now())){
            refreshToken();
        }
        return currentToken;
    }

    public void refreshToken() throws TokenFetchException {
        TokenRenewalResponse renewalResponse = tokenRetrieval.retrieveToken(params,
                deutTokenRenewalUri,
                new TypeReference<DeutscheTokenRenewalResponse>(){});

        currentToken = renewalResponse.getAccessToken();
        tokenValidFor = renewalResponse.getSecondsUntilExpiring();

        lastRefreshDayTime = LocalDateTime.now();
    }

    @PostConstruct
    private void init(){
        try {
            System.out.println("post construct of deutsche token");
            params = new LinkedMultiValueMap<>();
            params.add("client_id", deutClientId);
            params.add("client_id", deutClientId);
            params.add("client_secret", deutClientSecret);
            params.add("grant_type", "refresh_token");
            params.add("refresh_token", oAuthToken);

            refreshToken();

        } catch (TokenFetchException ex) {
            System.out.println("post construct failed");
            ex.printStackTrace();
            System.out.println("Token negotiation failed. Application clearance might have expired");
        }
    }
}
