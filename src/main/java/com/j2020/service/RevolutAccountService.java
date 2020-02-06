package com.j2020.service;

import com.j2020.model.TokenFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
