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
    public List<? extends PaymentResponse> createPayments(List<? extends Payment> payments) {
        String OAuthToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class);
        List<? extends PaymentResponse> responses = new ArrayList<>();

        //System.out.println("INTERMISSION::payments: " + payments.getClass() +"\n");
        //System.out.println(payments.get(0));

        ObjectMapper mapper = new ObjectMapper();

        List<RevolutPayment> pojos = mapper.convertValue(
                payments,
                new TypeReference<List<RevolutPayment>>() {});
        pojos.forEach(System.out::println);

        //payments = new ObjectMapper().convertValue(payments, new TypeReference<List<RevolutPayment>>(){});

        return transactionRetrieval.pushPayments(OAuthToken, Optional.empty(), paymentUrl, pojos, type);
    }

    // FIXME DELETE THIS AFTER DB WORKS
    @Override
    public String demo() {
        return null;
    }
}
