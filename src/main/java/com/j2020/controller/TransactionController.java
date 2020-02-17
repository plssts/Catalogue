/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

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
generics simplified - nereik ?, ir taip marker interface yra;
constructor simplified - i mappers viska, validacija irgi;
tests - given - when - then sablonas; test method naming is SENSIBLE!
specific exceptions, plius exception handler ir specific exception handlers;
fina fields kurie yra final fields (nemazai);
add a logger;
move logic away from controller; adjust mapping;
update readme;
 */

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private BankingServiceFactory bankingService;
    private TransactionProcessingService transactionProcessing;

    public TransactionController(BankingServiceFactory bankingService, TransactionProcessingService transactionProcessing) {
        this.bankingService = bankingService;
        this.transactionProcessing = transactionProcessing;
    }

    @GetMapping
    public ResponseEntity<Map<Bank, List<Transaction>>> readTransactions() {
        List<Transaction> transactionsRevo = bankingService.retrieveTransactionService(Bank.REVOLUT).retrieveTransactionData(Optional.empty());

        List<String> ibans = bankingService.retrieveAccountService(Bank.DEUTSCHE)
                .retrieveAccountData(Optional.empty()).stream()
                .map(Account::getAccountId).collect(Collectors.toList());
        List<Transaction> transactionsDeut = bankingService.retrieveTransactionService(Bank.DEUTSCHE).retrieveTransactionData(Optional.of(ibans));

        Map<Bank, List<Transaction>> outcome = new EnumMap<>(Bank.class);
        outcome.put(Bank.REVOLUT, transactionsRevo);
        outcome.put(Bank.DEUTSCHE, transactionsDeut);

        return ok(outcome);
    }

    @PostMapping
    public ResponseEntity<Map<Bank, List<PaymentResponse>>> createPayments(@RequestBody Map<Bank, List<GeneralPayment>> params){
        logger.info("Creating payments for {}", params.keySet());
        Map<Bank, List<PaymentResponse>> outcome = transactionProcessing.initiatePaymentRequests(params);

        return ok(outcome);
    }
}
