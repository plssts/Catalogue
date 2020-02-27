package com.j2020.service;

import com.j2020.Constants;
import com.j2020.model.BatchOfPayments;
import com.j2020.model.BatchOfPaymentsMessage;
import com.j2020.model.GeneralPayment;
import com.j2020.repository.PaymentBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmsTransactionProducer {
    private static final Logger logger = LoggerFactory.getLogger(JmsTransactionProducer.class);
    private PaymentBatchRepository batchRepository;
    private JmsTemplate jmsTemplate;

    public JmsTransactionProducer(JmsTemplate jmsTemplate, PaymentBatchRepository batchRepository) {
        this.jmsTemplate = jmsTemplate;
        this.batchRepository = batchRepository;
    }

    public BatchOfPaymentsMessage sendPayments(List<GeneralPayment> payments) {
        BatchOfPayments newBatch = new BatchOfPayments();
        Long newId = batchRepository.save(newBatch).getId();

        System.out.println("Anybody out there? " + batchRepository.findById(newId)); // FIXME remove this

        logger.info("Sending to queue {} payments with BOP id {}", payments.size(), newId);

        payments.forEach(payment -> {
            payment.setBatchId(newId);
            jmsTemplate.convertAndSend(Constants.JMS_TRANSACTION_QUEUE, payment);
        });

        logger.info("Returning. All payments are now in queue.");

        BatchOfPaymentsMessage response = new BatchOfPaymentsMessage();
        response.setText("Payments are being processed. Check their status with the following batch id.");
        response.setBatchId(newId);

        return response;
    }
}
