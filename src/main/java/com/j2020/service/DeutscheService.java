package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j2020.model.DeutscheAccountData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
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
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        DeutscheAccountData[] accounts;
        ObjectMapper beautifier = new ObjectMapper();
        beautifier.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            response = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);
            accounts = beautifier.readValue(response.getBody(), DeutscheAccountData[].class);
            return beautifier.writerWithDefaultPrettyPrinter().writeValueAsString(accounts);
        } catch(JsonProcessingException | HttpClientErrorException ex){
            return "An error has occurred.";
        }
    }
}
