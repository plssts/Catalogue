package com.j2020.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.j2020.model.AccessTokenDeutscheRenewalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class DeutscheRenewalService implements TokenRenewalService {
    @Autowired
    private TokenRequestRetrievalService tokenRetrieval;

    @Value("${deutscheTokenRenewal.deutTokenRenewalUri}")
    private String deutTokenRenewalUri;

    @Value("${deutscheTokenRenewal.deutClientId}")
    private String deutClientId;

    @Value("${deutscheTokenRenewal.deutClientSecret}")
    private String deutClientSecret;

    public String getNewToken(String OAuthToken){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", deutClientId);
        params.add("client_secret", deutClientSecret);
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", OAuthToken);

        return tokenRetrieval.processRequest(params,
                deutTokenRenewalUri,
                new TypeReference<AccessTokenDeutscheRenewalResponse>(){});
    }
}
