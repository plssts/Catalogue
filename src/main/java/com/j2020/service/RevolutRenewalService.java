package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.AccessTokenRevolutRenewalResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RevolutRenewalService implements TokenRenewalService {
    private final String revoTokenRenewalUri = "https://sandbox-b2b.revolut.com/api/1.0/auth/token";

    public String getNewToken(String OAuthJWT){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", "oa_sand_vx6Wl1iCfh7Ou1XEPMNCDIrWiVe0ip6YtG5Nifj-TKc"); // TODO move to final somewhere
        params.add("client_id", "vuIvOZuC-qQqJiOENV1Nrb8I26oqQtszWDLqSdYLSKc"); // So far a single client
        params.add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer"); // TODO move to final somewhere
        params.add("client_assertion", OAuthJWT);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.postForEntity(revoTokenRenewalUri, request, String.class);
            return new ObjectMapper().readValue(response.getBody(), AccessTokenRevolutRenewalResponse.class).getAccessToken();
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "";
        }
    }
}
