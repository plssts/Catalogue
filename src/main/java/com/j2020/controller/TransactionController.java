/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.*;
import com.j2020.service.BankingServiceFactory;
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
 */

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private BankingServiceFactory bankingService;

    public TransactionController(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
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
    public ResponseEntity<Map<Bank, List<PaymentResponse>>> createPayments(@RequestBody Map<Bank, List> params){
        List<PaymentResponse> revolutResponses = bankingService.retrieveTransactionService(Bank.REVOLUT).createPayments(params.get(Bank.REVOLUT));
        List<PaymentResponse> deutscheResponses = bankingService.retrieveTransactionService(Bank.DEUTSCHE).createPayments(params.get(Bank.DEUTSCHE));

        Map<Bank, List<PaymentResponse>> outcome = new EnumMap<>(Bank.class);
        outcome.put(Bank.REVOLUT, revolutResponses);
        outcome.put(Bank.DEUTSCHE, deutscheResponses);
        return ok(outcome);
    }
}
