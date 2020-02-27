package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.Constants;
import com.j2020.model.BatchOfPayments;
import com.j2020.model.GeneralPayment;
import com.j2020.model.PaymentResponse;
import com.j2020.repository.PaymentBatchRepository;
import com.j2020.repository.TransactionsForBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JmsTransactionConsumer {
    private final Logger logger = LoggerFactory.getLogger(JmsTransactionConsumer.class);
    private BankingServiceFactory serviceFactory;

    @Autowired
    private PaymentBatchRepository batchRepository;

    @Autowired
    private TransactionsForBatchRepository transactions;

    public JmsTransactionConsumer(BankingServiceFactory serviceFactory/*,
                                  PaymentBatchRepository batchRepository*/) {
        this.serviceFactory = serviceFactory;
        //this.batchRepository = batchRepository;
    }

    //@Async(Constants.JMS_ASYNC_EXECUTOR)
    @JmsListener(destination = Constants.JMS_TRANSACTION_QUEUE)
    public void onMessage(GeneralPayment payment) {
        List<GeneralPayment> toProcess = new ArrayList<>();
        toProcess.add(payment);
        try {
            System.out.println("[CONS] payment has batchid as: " + payment.getBopid());
            List<PaymentResponse> responses = serviceFactory.retrieveTransactionService(payment.getBank()).createPayments(toProcess);

            updateBatchCounters(payment.getBopid());

            //System.out.println("Anyone on the consumer? " + batchRepository.findById(payment.getBatchId())); // FIXME remove this

            /*Optional<BatchOfPayments> batch = batchRepository.findById(payment.getBatchId());

            TransactionStatusCheck status = new TransactionStatusCheck();
            status.setPaymentId(responses.get(0).getPaymentId());
            status.setTransactionStatus(responses.get(0).getStatus());

            if (!batch.isPresent()) {
                logger.error("No pre-created batch for this payment when there should have been one");
                return;
            }
            BatchOfPayments batchObject = batch.get();

            List<TransactionStatusCheck> collection = batchObject.getPayments();
            collection.add(status);
            batchObject.setPayments(collection);

            logger.info("Saving the new payment identification and status");

            batchRepository.save(batchObject);*/

        } catch (JsonProcessingException exception) {
            logger.error("Could not process {} because {}", payment, exception.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    private void updateBatchCounters(Long batchId) {
        Optional<BatchOfPayments> batch = batchRepository.findById(batchId);
        if (batch.isPresent()) {
            BatchOfPayments batchObject = batch.get();
            int size = transactions.findAllByBopid(batchObject.getId()).size();
            batchObject.setCountOfProcessedPayments(size);

            logger.info("Updated processed payments counter");

            batchRepository.save(batchObject);
        }
    }
}
