/**
 * @author Paulius Staisiunas
 */

package com.j2020.service.deutsche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.deutsche.DeutschePayment;
import com.j2020.model.deutsche.DeutschePaymentResponse;
import com.j2020.model.deutsche.DeutscheTransaction;
import com.j2020.repository.PaymentBatchRepository;
import com.j2020.repository.TransactionsForBatchRepository;
import com.j2020.service.TransactionRequestRetrievalService;
import com.j2020.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeutscheTransactionService implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DeutscheTransactionService.class);
    private final DeutscheTokenService tokenRenewal;
    private final TransactionRequestRetrievalService transactionRetrieval;
    private final DeutscheMapperService deutscheMapper;

    @Autowired
    private TransactionsForBatchRepository transactions;

    //@Autowired
    //private PaymentBatchRepository batchRepository;

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

        List<PaymentResponse> responses = new ArrayList<>();
        boolean hasFailed = false;

        try {
            responses = transactionRetrieval.pushPayments(
                    tokenRenewal.getToken(),
                    paymentUrl,
                    parsedPayments,
                    new ObjectMapper().getTypeFactory().constructType(DeutschePaymentResponse.class));
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            logger.error("HTTP SERVER ERROR OCCURRED");
            saveFailedStatus(payments.get(0));
            //updateBatchCounters(payments.get(0).getBopid());
            return new ArrayList<>();
        }

        TransactionStatusCheck status = new TransactionStatusCheck();
        status.setPaymentId(responses.get(0).getPaymentId());
        status.setTransactionStatus(responses.get(0).getStatus());
        status.setBopid(payments.get(0).getBopid());
        status.setBank(Bank.DEUTSCHE);
        status.setSourceAccount(payments.get(0).getSourceAccount());
        status.setDestinationAccount(payments.get(0).getDestinationAccount());

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
        //updateBatchCounters(payments.get(0).getBopid()); // FIXME move this to consumer, less duplication
        System.out.println("Count: " + transactions.findAllByBopid(payments.get(0).getBopid()).size());

        return new ArrayList<>();
    }

    private void saveFailedStatus(GeneralPayment payment) {
        logger.info("Saving the new payment FAIL");
        TransactionStatusCheck status = new TransactionStatusCheck();
        status.setPaymentId("fail");
        status.setTransactionStatus("fail");
        status.setBopid(payment.getBopid());
        status.setBank(Bank.DEUTSCHE);
        status.setSourceAccount(payment.getSourceAccount());
        status.setDestinationAccount(payment.getDestinationAccount());

        transactions.save(status);
        System.out.println("Count: " + transactions.findAllByBopid(payment.getBopid()).size());
    }

    /*@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    private void updateBatchCounters(Long batchId) {
        Optional<BatchOfPayments> batch = batchRepository.findById(batchId);
        if (batch.isPresent()) {
            BatchOfPayments batchObject = batch.get();
            batchObject.setCountOfProcessedPayments(batchObject.getCountOfProcessedPayments() + 1);

            logger.info("Plus one to processed payments");

            batchRepository.save(batchObject);
        }
    }*/

    @Override
    public boolean canProcessThisBank(Bank bankingService) {
        return bankingService.equals(Bank.DEUTSCHE);
    }
}
