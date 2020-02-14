/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.model.Transaction;
import com.j2020.service.BankingServiceFactory;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<Bank, List<? extends Account>>> readAccounts() {
        List<? extends Account> accountsRevo = bankingService.retrieveAccountService(Bank.REVOLUT).retrieveAccountData(Optional.empty());
        List<? extends Account> accountsDeut = bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData(Optional.empty());

        Map<Bank, List<? extends Account>> outcome = new EnumMap<>(Bank.class);
        outcome.put(Bank.REVOLUT, accountsRevo);
        outcome.put(Bank.DEUTSCHE, accountsDeut);

        return ok(outcome);
    }
}
