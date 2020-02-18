package com.j2020.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.controller.TransactionController;
import com.j2020.model.Account;
import com.j2020.model.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
//import sun.jvm.hotspot.gc.z.ZCollectedHeap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountProcessingService {
    private final BankingServiceFactory bankingService;
    private static final Logger logger = LoggerFactory.getLogger(AccountProcessingService.class);

    public AccountProcessingService(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    public Map<String, List<Account>> collectAccountResponse() throws JsonProcessingException {
        List<Account> accountsRevo = bankingService.retrieveAccountService(Bank.REVOLUT).retrieveAccountData();
        List<Account> accountsDeut = bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData();

        Map<String, List<Account>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), accountsRevo);
        outcome.put(Bank.DEUTSCHE.toString(), accountsDeut);

        return outcome;
    }
}
