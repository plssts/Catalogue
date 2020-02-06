package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.RevolutAccountData;
import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RevolutAccountService implements AccountService {
    @Autowired
    private RevolutRenewalService tokenRenewal;

    @Autowired
    private AccountRequestRetrievalService accountRetrieval;

    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    @Value("${revolutAccount.accountUrl}")
    private String accountUrl;

    @Override
    public String retrieveAccountData(){
        try {
            String OAuthToken = tokenRenewal.getNewToken(OAuthJWT);
            return accountRetrieval.processRequest(OAuthToken, accountUrl);
        } catch (TokenFetchException e) {
            return "Token negotiation failed. Application clearance might have expired";
        }
    }

    @Override
    public String retrieveSpecificAccount(String account) {
        try {
            String OAuthToken = tokenRenewal.getNewToken(OAuthJWT);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(accountUrl).pathSegment(account);
            return accountRetrieval.processRequest(OAuthToken, uriBuilder.toUriString());
        } catch (TokenFetchException e) {
            return "Token negotiation failed. Application clearance might have expired";
        }
    }
}
