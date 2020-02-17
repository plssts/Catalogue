/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.service.BankingServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AccountController {
    private BankingServiceFactory bankingService;

    public AccountController(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<Map<String, List<Account>>> readAccounts() {
        List<Account> accountsRevo = bankingService.retrieveAccountService(Bank.REVOLUT).retrieveAccountData(Optional.empty());
        List<Account> accountsDeut = bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData(Optional.empty());

        Map<String, List<Account>> outcome = new HashMap<>();
        outcome.put(Bank.REVOLUT.toString(), accountsRevo);
        outcome.put(Bank.DEUTSCHE.toString(), accountsDeut);

        return ok(outcome);
    }
}
