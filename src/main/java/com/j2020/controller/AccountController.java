/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.service.AccountProcessingService;
import com.j2020.service.BankingServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountProcessingService accountProcessing;

    public AccountController(AccountProcessingService accountProcessing) {
        this.accountProcessing = accountProcessing;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Account>>> readAccounts() throws JsonProcessingException {
        Map<String, List<Account>> outcome = accountProcessing.collectAccountResponse();

        return ok(outcome);
    }
}
