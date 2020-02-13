package com.j2020.service.revolut;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.TokenFetchException;
import com.j2020.model.Transaction;
import com.j2020.model.revolut.RevolutAccount;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutPaymentResponse;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RevolutTransactionService implements TransactionService {
    private RevolutTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;

    @Value("${revolutTransaction.transactionUrl}")
    private String transactionUrl;

    @Value("${revolutTransaction.paymentUrl}")
    private String paymentUrl;

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
    public List<? extends PaymentResponse> createPayments(List payments) {
        String OAuthToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class);
        List<? extends PaymentResponse> responses = new ArrayList<>();

        System.out.println("INTERMISSION::payments: " + payments.getClass() +"\n");
        System.out.println(payments.get(0));
        //payments = new ObjectMapper().convertValue(payments, new TypeReference<List<RevolutPayment>>(){});

        return transactionRetrieval.pushPayments(OAuthToken, Optional.empty(), paymentUrl, payments, type);
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
        {
	"REVOLUT":[
		{
			"request_id": "e0cbf84638264ee082a808b",
			"account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
			"receiver": {
			"counterparty_id": "b3314028-6158-4d11-8d4a-ef5e1bc9bc73",
			"account_id": "62670460-561e-4955-ba19-0b4c4df46566"
			},
			"amount": 1,
			"currency": "EUR",
			"reference": "test"
		},
		{
			"request_id": "e0cbf84638264ee082a848a",
			"account_id": "8a7d6a71-0bbc-4691-b04e-9ac7c54e0b5e",
			"receiver": {
			"counterparty_id": "b3314028-6158-4d11-8d4a-ef5e1bc9bc73",
			"account_id": "62670460-561e-4955-ba19-0b4c4df46566"
			},
			"amount": 1,
			"currency": "EUR",
			"reference": "test"
		}
	]
}
         */

        //generateRequestId();

        //return "";
    }

    // FIXME DELETE THIS AFTER DB WORKS
    @Override
    public String demo() {
        return null;
    }
}
