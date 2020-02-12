package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.Transaction;
import com.j2020.service.BankingServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        Map<Bank, List<? extends Transaction>> ret = new EnumMap<>(Bank.class);
        ret.put(Bank.REVOLUT, transactionsRevo);
        ret.put(Bank.DEUTSCHE, transactionsDeut);

        return ok(ret);
    }
}
