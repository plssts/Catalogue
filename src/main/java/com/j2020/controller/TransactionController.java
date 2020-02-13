package com.j2020.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j2020.model.*;
import com.j2020.model.revolut.RevolutPayment;
import com.j2020.model.revolut.RevolutTransaction;
import com.j2020.service.BankingServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/transactions")
    public ResponseEntity<List<? extends PaymentResponse>> createPayment(@RequestBody Map<Bank, List> params){
        //return ok(params.get(Bank.REVOLUT));
        //List payments = params.get(Bank.REVOLUT);
        //payments.forEach(System.out::println);
        return ok(bankingService.retrieveTransactionService(Bank.REVOLUT).createPayments(params.get(Bank.REVOLUT)));
    }

    @GetMapping("/trans") // FIXME remove this properly
    public ResponseEntity<String> postTransactions(){
        return ok(bankingService.retrieveTransactionService(Bank.DEUTSCHE).demo());
    }
}
