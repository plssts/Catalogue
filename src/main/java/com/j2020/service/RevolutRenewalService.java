package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.AccessTokenRevolutRenewalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutRenewalService implements TokenRenewalService {
    @Value("${revolutTokenRenewal.revoTokenRenewalUri}")
    private String revoTokenRenewalUri;

    @Value("${revolutTokenRenewal.revoRefreshToken}")
    private String revoRefreshToken;

    @Value("${revolutTokenRenewal.revoClientId}")
    private String revoClientId;

    @Value("${revolutTokenRenewal.clAssertType}")
    private String clAssertType;

    public String getNewToken(String OAuthJWT){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", revoRefreshToken);
        params.add("client_id", revoClientId);
        params.add("client_assertion_type", clAssertType);
        params.add("client_assertion", OAuthJWT);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.postForEntity(revoTokenRenewalUri,
                    request,
                    String.class);

            return new ObjectMapper()
                    .readValue(response.getBody(), AccessTokenRevolutRenewalResponse.class)
                    .getAccessToken();
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "";
        }
    }
}
