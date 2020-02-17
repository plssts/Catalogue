package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.deutsche.*;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class DeutscheTransactionService implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DeutscheTransactionService.class);
    private DeutscheTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;
    private DeutscheMapperService deutscheMapper;

    @Value("${deutscheTransaction.ibanAvailableUrlPrepend}")
    private String ibanOnUrlPrepend;

    @Value("${deutscheTransaction.transactionUrl}")
    private String transactionUrl;

    @Value("${deutscheTransaction.paymentUrl}")
    private String paymentUrl;

    public DeutscheTransactionService(DeutscheTokenService tokenRenewal, TransactionRequestRetrievalService transactionRetrieval, DeutscheMapperService deutscheMapper) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
        this.deutscheMapper = deutscheMapper;
    }

    @Override
    public List<Transaction> retrieveTransactionData(Optional<List<String>> ibans) {
        try {
            if (!ibans.isPresent()) {
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

    @Override
    public List<PaymentResponse> createPayments(List<GeneralPayment> payments) {
        if (payments == null) {
            logger.info("No payments included for Deutsche Bank. Skipping.");
            return new ArrayList<>();
        }

        List<DeutschePayment> parsedPayments = new ArrayList<>();

        logger.info("Constructing and validating Deutsche Bank payments");
        payments.forEach(payment -> parsedPayments.add(deutscheMapper.toDeutschePayment(payment)));

        return transactionRetrieval.pushPayments(tokenRenewal.getToken(), Optional.empty(), paymentUrl, parsedPayments, new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class));
    }
}
