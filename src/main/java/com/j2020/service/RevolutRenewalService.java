package com.j2020.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.j2020.model.AccessTokenRevolutRenewalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class RevolutRenewalService implements TokenRenewalService {
    @Autowired
    private TokenRequestRetrievalService tokenRetrieval;

    @Value("${revolutTokenRenewal.revoTokenRenewalUri}")
    private String revoTokenRenewalUri;

    @Value("${revolutTokenRenewal.revoRefreshToken}")
    private String revoRefreshToken;

    @Value("${revolutTokenRenewal.revoClientId}")
    private String revoClientId;

    @Value("${revolutTokenRenewal.clAssertType}")
    private String clAssertType;

    public String getNewToken(String OAuthJWT){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", revoRefreshToken);
        params.add("client_id", revoClientId);
        params.add("client_assertion_type", clAssertType);
        params.add("client_assertion", OAuthJWT);

        return tokenRetrieval.processRequest(params,
                revoTokenRenewalUri,
                new TypeReference<AccessTokenRevolutRenewalResponse>(){});
    }
}
