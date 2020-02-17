package com.j2020.service.revolut;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutschePaymentResponse;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutPaymentResponse;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.BankingServiceFactory;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RevolutTransactionService implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(RevolutTransactionService.class);
    private RevolutTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;
    private RevolutMapperService revolutMapper;

    @Value("${revolutTransaction.transactionUrl}")
    private String transactionUrl;

    @Value("${revolutTransaction.paymentUrl}")
    private String paymentUrl;

    public RevolutTransactionService(RevolutTokenService tokenRenewal, TransactionRequestRetrievalService transactionRetrieval, RevolutMapperService revolutMapper) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
        this.revolutMapper = revolutMapper;
    }

    @Override
    public List<Transaction> retrieveTransactionData(Optional<List<String>> accountIds) {
        try {
            String OAuthToken = tokenRenewal.getToken();
            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutTransaction.class);

            return transactionRetrieval.retrieveTransactions(OAuthToken, transactionUrl, type);
        } catch (HttpClientErrorException exception) {
            throw new TokenFetchException();
        }
    }

    @Override
    public List<PaymentResponse> createPayments(List<GeneralPayment> payments) {
        if (payments == null) {
            logger.info("No payments included for Revolut. Skipping.");
            return new ArrayList<>();
        }

        /*String OAuthToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class);

        ObjectMapper mapper = new ObjectMapper();
        List<RevolutPayment> castedObjects = mapper.convertValue(payments, new TypeReference<List<RevolutPayment>>() {
        });*/

        List<RevolutPayment> parsedPayments = new ArrayList<>();

        payments.forEach(payment -> parsedPayments.add(revolutMapper.toRevolutPayment(payment)));

        return transactionRetrieval.pushPayments(tokenRenewal.getToken(), Optional.empty(), paymentUrl, parsedPayments, new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class));
    }
}
