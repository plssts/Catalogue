package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.AccessTokenDeutscheRenewalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DeutscheRenewalService implements TokenRenewalService {
    @Value("${deutscheTokenRenewal.deutTokenRenewalUri}")
    private String deutTokenRenewalUri;

    @Value("${deutscheTokenRenewal.deutClientId}")
    private String deutClientId;

    @Value("${deutscheTokenRenewal.deutClientSecret}")
    private String deutClientSecret;

    public String getNewToken(String OAuthToken){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", deutClientId);
        params.add("client_secret", deutClientSecret);
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", OAuthToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.postForEntity(deutTokenRenewalUri, request, String.class);
            return new ObjectMapper()
                    .readValue(response.getBody(), AccessTokenDeutscheRenewalResponse.class)
                    .getAccessToken();
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "";
        }
    }
}
