package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TransactionRequestRetrievalService {
    @Value("${revolutTransaction.MAX_REQID_LENGTH}")
    private int MAX_REQID_LENGTH;

    public List<? extends Transaction> retrieveTransactions(String token, String url, JavaType reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        try {
            response = template.exchange(url, HttpMethod.GET, new HttpEntity(headers), String.class);

            String content = response.getBody();
            StringBuilder builder = new StringBuilder(content);
            if (!content.startsWith("[")) {
                builder.insert(0, "[").append("]");
            }

            return new ObjectMapper().readValue(builder.toString(), reference);
        } catch (JsonProcessingException | HttpClientErrorException exception) {
            throw new TokenFetchException();
        }
    }

    public List<? extends PaymentResponse> pushPayments(String token, Optional<Map<String, String>> extraHeaders, String url, List<? extends Payment> payments, JavaType reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (extraHeaders.isPresent()) { // TODO test and fix for DB
            headers.set("otp", extraHeaders.get().get("otp"));
            headers.set("idempotency-id", extraHeaders.get().get("idempotency-id"));
        }

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        List responses = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            for (Payment payment : payments) {
                payment.setIdentifyingInformation(generateRequestId());
                response = template.exchange(url, HttpMethod.POST, new HttpEntity<>(payment, headers), String.class);
                responses.add(mapper.readValue(response.getBody(), reference));
            }

            return responses;
        } catch (JsonProcessingException | HttpClientErrorException exception) {
            exception.printStackTrace();
            throw new TokenFetchException();
        }
    }

    private String generateRequestId() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        while (builder.length() < MAX_REQID_LENGTH) {
            builder.append(Integer.toHexString(random.nextInt()));
        }

        return builder.toString().substring(0, MAX_REQID_LENGTH);
    }
}
