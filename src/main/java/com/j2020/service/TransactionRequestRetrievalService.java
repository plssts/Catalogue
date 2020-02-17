package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutscheSepaPaymentRequestData;
import com.j2020.service.deutsche.DeutscheMultiFactorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TransactionRequestRetrievalService {
    private DeutscheMultiFactorService deutscheMultiFactor;

    @Value("${revolutTransaction.MAX_REQID_LENGTH}")
    private int MAX_REQID_LENGTH;

    public TransactionRequestRetrievalService(DeutscheMultiFactorService deutscheMultiFactor) {
        this.deutscheMultiFactor = deutscheMultiFactor;
    }

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

    // FIXME remove optional map
    public List<? extends PaymentResponse> pushPayments(String token, Optional<Map<String, String>> extraHeaders, String url, List<? extends Payment> payments, JavaType reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate template = new RestTemplateBuilder().build();
        ResponseEntity<String> response;
        List responses = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            for (Payment payment : payments) {
                if (payment instanceof DeutschePayment) {
                    System.out.println("Deutsche payment processing now");
                    Map<String, String> headerInfo = deutscheMultiFactor.prepareAuthorisation(token,
                            ((DeutschePayment) payment).getCreditorAccount().getIban(),
                            ((DeutschePayment) payment).getInstructedAmount().getCurrencyCode(),
                            DeutscheSepaPaymentRequestData.formatValue(((DeutschePayment) payment).getInstructedAmount().getAmount()));
                    headers.set("otp", headerInfo.get("otp"));
                    headers.set("idempotency-id", headerInfo.get("idempotency-id"));
                } else {
                    payment.setIdentifyingInformation(generateIdentification(/*extraHeaders*/));
                }
                response = template.exchange(url, HttpMethod.POST, new HttpEntity<>(payment, headers), String.class);
                responses.add(mapper.readValue(response.getBody(), reference));
            }

            return responses;
        } catch (JsonProcessingException | HttpClientErrorException exception) {
            exception.printStackTrace();
            throw new TokenFetchException();
        }
    }

    private String generateIdentification() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        while (builder.length() < MAX_REQID_LENGTH) {
            builder.append(Integer.toHexString(random.nextInt()));
        }

        return builder.toString().substring(0, MAX_REQID_LENGTH);
    }
}
