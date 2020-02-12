package com.j2020.service.revolut;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.TokenFetchException;
import com.j2020.model.Transaction;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class RevolutTransactionService implements TransactionService {
    private RevolutTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;

    @Value("${revolutTransaction.transactionUrl}")
    private String transactionUrl;

    public RevolutTransactionService(RevolutTokenService tokenRenewal, TransactionRequestRetrievalService transactionRetrieval) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
    }

    @Override
    public List<? extends Transaction> retrieveTransactionData(Optional<List<String>> accountIds) {
        try {
            String OAuthToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutTransaction.class);

            return transactionRetrieval.retrieveTransactions(OAuthToken, transactionUrl, type);
        } catch (HttpClientErrorException exception) {
            throw new TokenFetchException();
        }
    }
}
