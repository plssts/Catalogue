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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class DeutscheTransactionService implements TransactionService {
    private DeutscheTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;

    @Value("${deutscheAccount.transactionUrl}")
    private String transactionUrl;

    @Override
    public List<? extends Transaction> retrieveTransactionData(Optional<List<String>> ibans) {
        try {
            if (!ibans.isPresent()){
                return null;
            }

            String accessToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheTransaction.class);

            return transactionRetrieval.retrieveTransactions(accessToken, transactionUrl, type);
        } catch (HttpClientErrorException exception) {
            throw new TokenFetchException();
        }
    }
}
