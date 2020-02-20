/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.revolut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutPaymentResponse;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RevolutTransactionService implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(RevolutTransactionService.class);
    private final RevolutTokenService tokenRenewal;
    private final TransactionRequestRetrievalService transactionRetrieval;
    private final RevolutMapperService revolutMapper;

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
    public List<Transaction> retrieveTransactionData(List<String> accountIds) throws JsonProcessingException {
        String OAuthToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, RevolutTransaction.class);

        return transactionRetrieval.retrieveTransactions(OAuthToken, transactionUrl, type);
    }

    @Override
    public List<PaymentResponse> createPayments(List<GeneralPayment> payments) throws JsonProcessingException {
        if (payments == null) {
            logger.info("No payments included for Revolut. Skipping.");
            return new ArrayList<>();
        }

        List<RevolutPayment> parsedPayments = new ArrayList<>();

        logger.info("Constructing and validating Revolut payments");
        payments.forEach(payment -> parsedPayments.add(revolutMapper.toRevolutPayment(payment)));

        return transactionRetrieval.pushPayments(tokenRenewal.getToken(), paymentUrl, parsedPayments, new ObjectMapper().getTypeFactory().constructType(RevolutPaymentResponse.class));
    }
}
