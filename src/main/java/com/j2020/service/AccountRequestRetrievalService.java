/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountRequestRetrievalService {
    public List<Account> processRequest(String token, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.exchange(url,
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    new ParameterizedTypeReference<List<Account>>() {
                    });

            return response.getBody();
        } catch (HttpClientErrorException ex){
            throw ex;
        }
    }
}
