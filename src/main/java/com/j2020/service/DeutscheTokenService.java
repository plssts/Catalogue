/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.j2020.model.AccessTokenDeutscheRenewalResponse;
import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;

@Service
public class DeutscheTokenService implements TokenService {
    @Autowired
    private TokenRequestRetrievalService tokenRetrieval;

    @Value("${deutscheTokenRenewal.deutTokenRenewalUri}")
    private String deutTokenRenewalUri;

    @Value("${deutscheTokenRenewal.deutClientId}")
    private String deutClientId;

    @Value("${deutscheTokenRenewal.deutClientSecret}")
    private String deutClientSecret;

    @Value("${deutscheTokenRenewal.OAuthToken}")
    private String OAuthToken;

    private String currentToken;

    private MultiValueMap<String, String> params;

    public String getToken(){
        return currentToken;
    }

    public void refreshToken() throws TokenFetchException {
        currentToken = tokenRetrieval.processRequest(params,
                deutTokenRenewalUri,
                new TypeReference<AccessTokenDeutscheRenewalResponse>(){});
    }

    @PostConstruct
    private void init(){
        try {
            params = new LinkedMultiValueMap<>();
            params.add("client_id", deutClientId);
            params.add("client_secret", deutClientSecret);
            params.add("grant_type", "refresh_token");
            params.add("refresh_token", OAuthToken);

            refreshToken();

        } catch (TokenFetchException ex) {
            ex.printStackTrace();
            System.out.println("Token negotiation failed. Application clearance might have expired");
        }
    }
}
