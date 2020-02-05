package com.j2020.service;

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

@Service
public class DeutscheService implements AccountService {
    @Autowired
    private DeutscheRenewalService tokenRenewal;

    @Value("${deutscheTokenRenewal.OAuthToken}")
    private String OAuthToken;

    @Override
    public String retrieveAccountData() {
        String url = "https://simulator-api.db.com/gw/dbapi/v1/cashAccounts";

        // TODO only request a new token if the current one is broken
        String accessToken = tokenRenewal.getNewToken(OAuthToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);
        } catch(HttpClientErrorException ex){
            return "An error has occurred.";
        }

        return response.getBody();
    }
}
