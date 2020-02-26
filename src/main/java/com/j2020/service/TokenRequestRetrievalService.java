/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.TokenRenewalResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenRequestRetrievalService {
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    public TokenRequestRetrievalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TokenRenewalResponse retrieveToken(MultiValueMap<String, String> params, String uri, JavaType reference) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        return new ObjectMapper().readValue(response.getBody(), reference);
    }
}
