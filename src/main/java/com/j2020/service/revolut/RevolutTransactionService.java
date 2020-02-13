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
import java.util.Random;

@Service
public class RevolutTransactionService implements TransactionService {
    private RevolutTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;

    @Value("${revolutTransaction.transactionUrl}")
    private String transactionUrl;

    @Value("${revolutTransaction.MAX_REQID_LENGTH}")
    private int MAX_REQID_LENGTH;

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

    @Override
    public String createPayment() {
        /*
        Demo sample transaction
        {
        "id":"63e0c9ee-8884-47e6-82d5-a0392d47808b",
        "type":"transfer",
        "state":"completed",
        "reference":"payment",
        "merchant":null,
        "card":null,
        "request_id":null,
        "created_at":"2020-02-04T09:12:53.776451Z",
        "updated_at":"2020-02-04T09:12:53.776451Z",
        "completed_at":"2020-02-04T09:12:53.778209Z",
        "legs":[
            {"counterparty":{
                "account_type":"revolut",
                "account_id":"88888888-4444-4444-4444-222222222222"
                },
             "amount":-210.0,
             "currency":"EUR",
             "description":"To Rory Pearson",
             "balance":3490.0,
             "leg_id":"3f4dcdc5-0625-4e26-8077-cfe23cef52ae",
             "account_id":"8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e"
            }
        ]}
        */

        /*
        Payment data

        {
          "request_id": "e0cbf84637264ee082a848b",
          "account_id": "bdab1c20-8d8c-430d-b967-87ac01af060c",
          "receiver": {
            "counterparty_id": "5138z40d1-05bb-49c0-b130-75e8cf2f7693",
            "account_id": "db7c73d3-b0df-4e0e-8a9a-f42aa99f52ab"
          },
          "amount": 123.11,
          "currency": "EUR",
          "reference": "Invoice payment #123"
        }
         */

        return "";
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
