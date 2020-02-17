package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.Payment;
import com.j2020.model.PaymentResponse;
import com.j2020.model.TokenFetchException;
import com.j2020.model.Transaction;
import com.j2020.model.deutsche.*;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
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
    private DeutscheTokenService tokenRenewal;
    private TransactionRequestRetrievalService transactionRetrieval;
    private DeutscheMultiFactorService multiFactor;

    @Value("${deutscheTransaction.ibanAvailableUrlPrepend}")
    private String ibanOnUrlPrepend;

    @Value("${deutscheTransaction.transactionUrl}")
    private String transactionUrl;

    public DeutscheTransactionService(DeutscheTokenService tokenRenewal, TransactionRequestRetrievalService transactionRetrieval, DeutscheMultiFactorService multiFactor) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
        this.multiFactor = multiFactor;
    }

    @Override
    public List<? extends Transaction> retrieveTransactionData(Optional<List<String>> ibans) {
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
    @Validated(DeutschePayment.class)
    // FIXME move urls to application.properties
    public List<? extends PaymentResponse> createPayments(List<? extends Payment> payments) {
        if (payments.isEmpty()){
            return new ArrayList<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        List<@Valid DeutschePayment> castedObjects = mapper.convertValue(payments, new TypeReference<List<@Valid DeutschePayment>>() {
        });

        String paymentUrl = "https://simulator-api.db.com/gw/dbapi/paymentInitiation/payments/v1/instantSepaCreditTransfers";

        return transactionRetrieval.pushPayments(tokenRenewal.getToken(), Optional.empty(), paymentUrl, castedObjects, new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class));

    }
}
