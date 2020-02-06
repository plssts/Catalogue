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
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RevolutAccountService implements AccountService {
    @Autowired
    private RevolutRenewalService tokenRenewal;

    @Value("${revolutTokenRenewal.OAuthJWT}")
    private String OAuthJWT;

    @Value("${revolutAccount.accountUrl}")
    private String accountUrl;

    @Override
    public String retrieveAccountData(){
        // TODO only request a new token if the current one is broken
        String OAuthToken = tokenRenewal.getNewToken(OAuthJWT);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OAuthToken);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        RevolutAccountData[] accounts;
        ObjectMapper beautifier = new ObjectMapper();
        try {
            response = template.exchange(accountUrl,
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    String.class);

            accounts = beautifier.readValue(response.getBody(), RevolutAccountData[].class);
            return beautifier.writerWithDefaultPrettyPrinter().writeValueAsString(accounts);
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "An error has occurred.";
        }
    }

    @Override
    public String retrieveSpecificAccount(String account) {
        // TODO fix token regen the same way as above
        String OAuthToken = tokenRenewal.getNewToken(OAuthJWT);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OAuthToken);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        RevolutAccountData singleAccount;
        ObjectMapper beautifier = new ObjectMapper();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(accountUrl).pathSegment(account);

        try {
            response = template.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    String.class);

            singleAccount = beautifier.readValue(response.getBody(), RevolutAccountData.class);
            return beautifier.writerWithDefaultPrettyPrinter().writeValueAsString(singleAccount);
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "An error has occurred.";
        }
    }
}
