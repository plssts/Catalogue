/**
 * @author Paulius Staisiunas
 */

package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.Transaction;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.service.deutsche.DeutscheMultiFactorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TransactionRequestRetrievalService {
    private final DeutscheMultiFactorService deutscheMultiFactor;
    private static final Logger logger = LoggerFactory.getLogger(TransactionRequestRetrievalService.class);

    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    private Random random = new Random();

    @Value("${revolutTransaction.maxReqIdLength}")
    private int maxReqIdLength;

    public TransactionRequestRetrievalService(DeutscheMultiFactorService deutscheMultiFactor,
                                              RestTemplate restTemplate) {
        this.deutscheMultiFactor = deutscheMultiFactor;
        this.restTemplate = restTemplate;
    }

    public List<Transaction> retrieveTransactions(String token, String url, JavaType reference) throws JsonProcessingException {
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

    public List<PaymentResponse> pushPayments(String token, String url, List<? extends Payment> payments, JavaType reference) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response;
        List<PaymentResponse> responses = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (Payment payment : payments) {
            if (payment instanceof DeutschePayment) {
                Map<String, String> headerInfo = deutscheMultiFactor.prepareAuthorisation(token,
                        ((DeutschePayment) payment).getCreditorAccount().getIban(),
                        ((DeutschePayment) payment).getInstructedAmount().getCurrencyCode(),
                        ((DeutschePayment) payment).getInstructedAmount().getAmount());
                headers.set("otp", headerInfo.get("otp"));
                headers.set("idempotency-id", headerInfo.get("idempotency-id"));
            } else {
                payment.setIdentifyingInformation(generateIdentification());
            }

            logger.info("Processing {}", payment);

            response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(payment, headers), String.class);
            responses.add(mapper.readValue(response.getBody(), reference));
        }

        return responses;
    }

    private String generateIdentification() {
        StringBuilder builder = new StringBuilder();

        while (builder.length() < maxReqIdLength) {
            builder.append(Integer.toHexString(random.nextInt()));
        }

        return builder.toString().substring(0, maxReqIdLength);
    }
}
