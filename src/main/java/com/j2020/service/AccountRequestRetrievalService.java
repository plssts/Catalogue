/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountRequestRetrievalService {
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    public AccountRequestRetrievalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Account> retrieveAccounts(String token, String url, JavaType reference) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);

        String content = response.getBody();
        StringBuilder builder = new StringBuilder(content);
        if (!content.startsWith("[")) {
            builder.insert(0, "[").append("]");
        }

        return new ObjectMapper().readValue(builder.toString(), reference);
    }
}
