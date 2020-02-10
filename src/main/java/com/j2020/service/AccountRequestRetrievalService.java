/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Account;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AccountRequestRetrievalService {
    public List<? extends Account> retrieveAccounts(String token, String url, JavaType reference){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            System.out.println("exchanging");
            response = template.exchange(url,
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    String.class);

            String content = response.getBody();
            StringBuilder sb = new StringBuilder(content);
            if (!content.startsWith("[")){
                sb.insert(0, "[").append("]");
            }
            System.out.println("exchange done:\n" + content);

            return new ObjectMapper().readValue(sb.toString(), reference);
        } catch (JsonProcessingException | HttpClientErrorException ex){
            throw new RuntimeException();
        }
    }
}
