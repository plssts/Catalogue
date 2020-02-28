/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.Constants;
import com.j2020.model.*;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutschePaymentResponse;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.model.exception.JsonProcessingExceptionLambdaWrapper;
import com.j2020.repository.TransactionsForBatchRepository;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeutscheTransactionService implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DeutscheTransactionService.class);
    private final DeutscheTokenService tokenRenewal;
    private final TransactionRequestRetrievalService transactionRetrieval;
    private final DeutscheMapperService deutscheMapper;
    private TransactionsForBatchRepository transactions;

    @Value("${deutscheTransaction.ibanAvailableUrlPrepend}")
    private String ibanOnUrlPrepend;

    @Value("${deutscheTransaction.transactionUrl}")
    private String transactionUrl;

    @Value("${deutscheTransaction.paymentUrl}")
    private String paymentUrl;

    public DeutscheTransactionService(DeutscheTokenService tokenRenewal,
                                      TransactionRequestRetrievalService transactionRetrieval,
                                      DeutscheMapperService deutscheMapper,
                                      TransactionsForBatchRepository transactions) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
        this.deutscheMapper = deutscheMapper;
        this.transactions = transactions;
    }

    @Override
    public List<GeneralTransaction> retrieveTransactionData(List<String> ibans) {
        if (ibans == null) {
            return new ArrayList<>();
        }

        String accessToken = tokenRenewal.getToken();
        JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(List.class, DeutscheTransaction.class);

        logger.info("Constructing and validating Deutsche Bank transactions");
        return ibans.stream()
                .flatMap(current -> {
                    try {
                        return transactionRetrieval
                                .retrieveTransactions(accessToken, UriComponentsBuilder
                                        .fromUriString(transactionUrl)
                                        .queryParam("iban", current)
                                        .toUriString(), type)
                                .stream()
                                .map(transaction -> deutscheMapper.toGeneralTransaction((DeutscheTransaction) transaction));
                    } catch (JsonProcessingException exception) {
                        throw new JsonProcessingExceptionLambdaWrapper(exception.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> createPayments(List<GeneralPayment> payments) throws JsonProcessingException {
        if (payments == null) {
            logger.info("No payments included for Deutsche Bank. Skipping.");
            return new ArrayList<>();
        }

        List<DeutschePayment> parsedPayments = new ArrayList<>();

        logger.info("Constructing and validating Deutsche Bank payments");
        payments.forEach(payment -> parsedPayments.add(deutscheMapper.toDeutschePayment(payment)));

        List<PaymentResponse> responses;

        try {
            responses = transactionRetrieval.pushPayments(
                    tokenRenewal.getToken(),
                    paymentUrl,
                    parsedPayments,
                    new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class));
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            logger.error("An HTTP error caused payment failure");
            saveFailedStatus(payments.get(0));
            return new ArrayList<>();
        }

        TransactionStatusCheck status = new TransactionStatusCheck();
        status.setPaymentId(responses.get(0).getPaymentId());
        status.setTransactionStatus(responses.get(0).getStatus());
        status.setBatchId(payments.get(0).getBatchId());
        status.setBank(Bank.DEUTSCHE);
        status.setSourceAccount(payments.get(0).getSourceAccount());
        status.setDestinationAccount(payments.get(0).getDestinationAccount());
        status.setAmount(payments.get(0).getAmount());

        logger.info("Saving the new payment identification and status");
        transactions.save(status);

        return new ArrayList<>();
    }

    private void saveFailedStatus(GeneralPayment payment) {
        logger.warn("Saving an entry for a Deutsche Bank payment that failed");

        TransactionStatusCheck status = new TransactionStatusCheck();
        status.setPaymentId(Constants.DISPLAY_FAILED_PAYMENT_ID + Instant.now().toEpochMilli());
        status.setTransactionStatus(Constants.DISPLAY_FAILED_PAYMENT_STATUS);
        status.setBatchId(payment.getBatchId());
        status.setBank(Bank.DEUTSCHE);
        status.setSourceAccount(payment.getSourceAccount());
        status.setDestinationAccount(payment.getDestinationAccount());
        status.setAmount(payment.getAmount());

        transactions.save(status);
    }

    @Override
    public boolean canProcessThisBank(Bank bankingService) {
        return bankingService.equals(Bank.DEUTSCHE);
    }
}
