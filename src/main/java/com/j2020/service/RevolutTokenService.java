/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.j2020.model.RevolutTokenRenewalResponse;
import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;

@Service
public class RevolutTokenService implements TokenService {
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

    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    private String currentToken;

    private static MultiValueMap<String, String> PARAM_MAP = new LinkedMultiValueMap<>();

    static {
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", revoRefreshToken);
        params.add("client_id", revoClientId);
        params.add("client_assertion_type", clAssertType);
        params.add("client_assertion", OAuthJWT);
    }



    public String getToken(){
        return currentToken;
    }

    public void refreshToken() throws TokenFetchException {
        currentToken = tokenRetrieval.retrieveToken(params,
                revoTokenRenewalUri,
                new TypeReference<RevolutTokenRenewalResponse>(){});
    }

    @PostConstruct
    private void init(){
        try {


            refreshToken();

        } catch (TokenFetchException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Token negotiation failed. Application clearance might have expired");
        }
    }
}
