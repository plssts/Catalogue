package com.j2020.controller;

import com.j2020.model.*;
import com.j2020.service.BankingServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class TransactionController {
    private BankingServiceFactory bankingService;

    public TransactionController(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map<Bank, List<? extends Transaction>>> readTransactions() {
        List<? extends Transaction> transactionsRevo = bankingService.retrieveTransactionService(Bank.REVOLUT).retrieveTransactionData(Optional.empty());

        List<String> ibans = bankingService.retrieveAccountService(Bank.DEUTSCHE)
                .retrieveAccountData(Optional.empty()).stream()
                .map(Account::getAccountId).collect(Collectors.toList());
        List<? extends Transaction> transactionsDeut = bankingService.retrieveTransactionService(Bank.DEUTSCHE).retrieveTransactionData(Optional.of(ibans));

        Map<Bank, List<? extends Transaction>> outcome = new EnumMap<>(Bank.class);
        outcome.put(Bank.REVOLUT, transactionsRevo);
        outcome.put(Bank.DEUTSCHE, transactionsDeut);

        return ok(outcome);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Map<Bank, List<? extends PaymentResponse>>> createPayments(@RequestBody Map<Bank, List> params){
        List<? extends PaymentResponse> revolutResponses = bankingService.retrieveTransactionService(Bank.REVOLUT).createPayments(params.get(Bank.REVOLUT));
        List<? extends PaymentResponse> deutscheResponses = bankingService.retrieveTransactionService(Bank.DEUTSCHE).createPayments(params.get(Bank.DEUTSCHE));

        Map<Bank, List<? extends PaymentResponse>> outcome = new EnumMap<>(Bank.class);
        outcome.put(Bank.REVOLUT, revolutResponses);
        outcome.put(Bank.DEUTSCHE, deutscheResponses);
        return ok(outcome);
    }

    @GetMapping("/trans") // FIXME remove this properly
    public ResponseEntity<String> postTransactions(){
        return ok(bankingService.retrieveTransactionService(Bank.DEUTSCHE).demo());
    }
}
