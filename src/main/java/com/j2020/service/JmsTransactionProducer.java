package com.j2020.service;

import com.j2020.Constants;
import com.j2020.model.BatchOfPayments;
import com.j2020.model.BatchOfPaymentsMessage;
import com.j2020.model.GeneralPayment;
import com.j2020.model.TransactionStatusCheck;
import com.j2020.repository.PaymentBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JmsTransactionProducer {
    private static final Logger logger = LoggerFactory.getLogger(JmsTransactionProducer.class);
    @Autowired
    private PaymentBatchRepository batchRepository;
    private JmsTemplate jmsTemplate;

    public JmsTransactionProducer(JmsTemplate jmsTemplate/*, PaymentBatchRepository batchRepository*/) {
        this.jmsTemplate = jmsTemplate;
        //this.batchRepository = batchRepository;
    }

    public BatchOfPaymentsMessage sendPayments(List<GeneralPayment> payments) {
        BatchOfPayments newBatch = new BatchOfPayments();
        BatchOfPayments current = batchRepository.save(newBatch);

        batchRepository.findAll().forEach(System.out::println);

        System.out.println("Anybody out there? " + batchRepository.findById(current.getId())); // FIXME remove this

        logger.info("Sending to queue {} payments with BOP id {}", payments.size(), current.getId());

        payments.forEach(payment -> {
            payment.setBatchOfPayments(current);
            payment.setBopid(current.getId());
            System.out.println("[PROD] payment has batchid as: " + payment.getBatchOfPayments().getId());
            jmsTemplate.convertAndSend(Constants.JMS_TRANSACTION_QUEUE, payment);
        });

        logger.info("Returning. All payments are now in queue.");

        BatchOfPaymentsMessage response = new BatchOfPaymentsMessage();
        response.setText("Payments are being processed. Check their status with the following batch id.");
        response.setBatchId(current.getId());

        return response;
    }
}
