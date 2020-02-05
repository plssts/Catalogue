package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.RevolutAccountData;
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
public class RevolutService implements AccountService {
    @Autowired
    private RevolutRenewalService tokenRenewal;

    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    @Override
    public String retrieveAccountData(){
        String url = "https://sandbox-b2b.revolut.com/api/1.0/accounts";

        // TODO only request a new token if the current one is broken
        String OAuthToken = tokenRenewal.getNewToken(OAuthJWT);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OAuthToken);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        RevolutAccountData[] accounts;
        ObjectMapper beautifier = new ObjectMapper();
        try {
            response = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);
            accounts = beautifier.readValue(response.getBody(), RevolutAccountData[].class);
            return beautifier.writerWithDefaultPrettyPrinter().writeValueAsString(accounts);
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "An error has occurred.";
        }
    }
}
