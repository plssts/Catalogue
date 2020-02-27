package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.Constants;
import com.j2020.model.*;
import com.j2020.repository.PaymentBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JmsTransactionConsumer {
    private final Logger logger = LoggerFactory.getLogger(JmsTransactionConsumer.class);
    private BankingServiceFactory serviceFactory;
    private PaymentBatchRepository batchRepository;

    public JmsTransactionConsumer(BankingServiceFactory serviceFactory,
                                  PaymentBatchRepository batchRepository) {
        this.serviceFactory = serviceFactory;
        this.batchRepository = batchRepository;
    }

    @Async(Constants.JMS_ASYNC_EXECUTOR)
    @JmsListener(destination = Constants.JMS_TRANSACTION_QUEUE)
    public void onMessage(GeneralPayment payment) {
        List<GeneralPayment> toProcess = new ArrayList<>();
        toProcess.add(payment);
        try {
            List<PaymentResponse> responses = serviceFactory.retrieveTransactionService(payment.getBank()).createPayments(toProcess);

            System.out.println("Anyone on the consumer? " + batchRepository.findById(payment.getBatchId())); // FIXME remove this

            Optional<BatchOfPayments> batch = batchRepository.findById(payment.getBatchId());

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

            batchRepository.save(batchObject);

        } catch (JsonProcessingException exception) {
            logger.error("Could not process {} because {}", payment, exception.getMessage());
        }
    }
}
