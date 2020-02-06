package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.TokenRenewalResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenRequestRetrievalService {
    public String processRequest(MultiValueMap<String, String> params,
                                 String uri,
                                 TypeReference<? extends TokenRenewalResponse> reference){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;

        try {
            response = template.postForEntity(uri,
                    request,
                    String.class);

            return new ObjectMapper()
                    .readValue(response.getBody(), reference)
                    .getAccessToken();
        } catch(JsonProcessingException | HttpClientErrorException ex){
            ex.printStackTrace();
            return "";
        }
    }
}
