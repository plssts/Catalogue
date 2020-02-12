package com.j2020.service.deutsche;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.TokenFetchException;
import com.j2020.model.Transaction;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeutscheTransactionService implements TransactionService {
    private DeutscheTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;

    @Value("${deutscheAccount.transactionUrl}")
    private String transactionUrl;

    public DeutscheTransactionService(DeutscheTokenService tokenRenewal, TransactionRequestRetrievalService transactionRetrieval) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
    }

    @Override
    public List<? extends Transaction> retrieveTransactionData(Optional<List<String>> ibans) {
        try {
            if (!ibans.isPresent()){
                return new ArrayList<>();
            }

            String accessToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheTransaction.class);

            return ibans.get().stream()
                    .flatMap(current -> transactionRetrieval
                            .retrieveTransactions(accessToken, UriComponentsBuilder
                                    .fromUriString(transactionUrl)
                                    .queryParam("iban", current)
                                    .toUriString(), type)
                            .stream())
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException exception) {
            throw new TokenFetchException();
        }
    }
}
