package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.j2020.model.DeutscheAccountData;
import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DeutscheAccountService implements AccountService {
    @Autowired
    private DeutscheRenewalService tokenRenewal;

    @Autowired
    private AccountRequestRetrievalService accountRetrieval;

    @Value("${deutscheTokenRenewal.OAuthToken}")
    private String OAuthToken;

    @Value("${deutscheAccount.accountUrl}")
    private String accountUrl;

    @Override
    public String retrieveAccountData(){
        try {
            String accessToken = tokenRenewal.getNewToken(OAuthToken);
            return accountRetrieval.processRequest(accessToken, accountUrl);
        } catch (TokenFetchException e) {
            return "Token negotiation failed. Application clearance might have expired";
        }
    }

    public String retrieveSpecificAccount(String iban){
        try {
            String accessToken = tokenRenewal.getNewToken(OAuthToken);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString(accountUrl)
                    .queryParam("iban", iban);
            return accountRetrieval.processRequest(accessToken, uriBuilder.toUriString());
        } catch (TokenFetchException e) {
            return "Token negotiation failed. Application clearance might have expired";
        }
    }
}
