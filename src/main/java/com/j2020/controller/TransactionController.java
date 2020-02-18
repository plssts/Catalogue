/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.*;
import com.j2020.service.BankingServiceFactory;
import com.j2020.service.TransactionProcessingService;
import com.j2020.service.deutsche.DeutscheMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

/*
constructor simplified - i mappers viska, validacija irgi;
tests : given - when - then sablonas;
add a logger;
update readme;
 */

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionProcessingService transactionProcessing;

    public TransactionController(TransactionProcessingService transactionProcessing) {
        this.transactionProcessing = transactionProcessing;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> readTransactions() throws JsonProcessingException {
        Map<String, List<Transaction>> outcome = transactionProcessing.collectPaymentResponse();

        return ok(outcome);
    }

    @PostMapping
    public ResponseEntity<Map<String, List<PaymentResponse>>> createPayments(@RequestBody Map<String, List<GeneralPayment>> params) throws JsonProcessingException {
        logger.info("Creating payments for {}", params.keySet());
        Map<String, List<PaymentResponse>> outcome = transactionProcessing.initiatePaymentRequests(params);

        return ok(outcome);
    }
}
