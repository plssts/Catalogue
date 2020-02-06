package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.j2020.model.DeutscheAccountData;
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

    @Value("${deutscheTokenRenewal.OAuthToken}")
    private String OAuthToken;

    @Value("${deutscheAccount.accountUrl}")
    private String accountUrl;

    @Override
    public String retrieveAccountData(){
        // TODO only request a new token if the current one is broken
        String accessToken = tokenRenewal.getNewToken(OAuthToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        //headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        DeutscheAccountData[] accounts;
        ObjectMapper beautifier = new ObjectMapper();
        //beautifier.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            response = template.exchange(accountUrl,
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    String.class);

            accounts = beautifier.readValue(response.getBody(), DeutscheAccountData[].class);
            return beautifier.writerWithDefaultPrettyPrinter().writeValueAsString(accounts);
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "An error has occurred.";
        }
    }

    public String retrieveSpecificAccount(String iban){
        // TODO fix token regen the same way as above
        String accessToken = tokenRenewal.getNewToken(OAuthToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        //headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        DeutscheAccountData[] accounts;
        ObjectMapper beautifier = new ObjectMapper();
        //beautifier.enable(SerializationFeature.INDENT_OUTPUT);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(accountUrl)
                .queryParam("iban", iban);

        try {
            response = template.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    String.class);

            accounts = beautifier.readValue(response.getBody(), DeutscheAccountData[].class);
            return beautifier.writerWithDefaultPrettyPrinter().writeValueAsString(accounts);
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "An error has occurred.";
        }
    }
}
