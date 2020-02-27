/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.deutsche.*;
import com.j2020.repository.PaymentBatchRepository;
import com.j2020.repository.TransactionsForBatchRepository;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeutscheTransactionService implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DeutscheTransactionService.class);
    private final DeutscheTokenService tokenRenewal;
    private final TransactionRequestRetrievalService transactionRetrieval;
    private final DeutscheMapperService deutscheMapper;

    @Autowired
    private TransactionsForBatchRepository transactions;

    @Value("${deutscheTransaction.ibanAvailableUrlPrepend}")
    private String ibanOnUrlPrepend;

    @Value("${deutscheTransaction.transactionUrl}")
    private String transactionUrl;

    @Value("${deutscheTransaction.paymentUrl}")
    private String paymentUrl;

    public DeutscheTransactionService(DeutscheTokenService tokenRenewal,
                                      TransactionRequestRetrievalService transactionRetrieval,
                                      DeutscheMapperService deutscheMapper) {
        this.tokenRenewal = tokenRenewal;
        this.transactionRetrieval = transactionRetrieval;
        this.deutscheMapper = deutscheMapper;
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

        List <PaymentResponse> responses = transactionRetrieval.pushPayments(
                tokenRenewal.getToken(),
                paymentUrl,
                parsedPayments,
                new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class));

        TransactionStatusCheck status = new TransactionStatusCheck();
        status.setPaymentId(responses.get(0).getPaymentId());
        status.setTransactionStatus(responses.get(0).getStatus());
        status.setBatch(payments.get(0).getBatchOfPayments());
        //status.setBopid(payments.get(0).getBopid());

        //Optional<BatchOfPayments> batch = batchRepository.findById(payments.get(0).getBatchId());

        /*if (!batch.isPresent()){
            logger.error("There was no batch repository for {}", payments.get(0).getBatchId());
            return new ArrayList<>();
        }*/

        //BatchOfPayments batchObject = batch.get();

        /*List<TransactionStatusCheck> collection = batchObject.getPayments();
        collection.add(status);
        batchObject.setPayments(collection);*/

        logger.info("Saving the new payment identification and status");
        //batchRepository.save(batchObject);
        transactions.save(status);

        transactions.findAll().forEach(System.out::println);

        return new ArrayList<>();
    }

    @Override
    public boolean canProcessThisBank(Bank bankingService) {
        return bankingService.equals(Bank.DEUTSCHE);
    }
}
