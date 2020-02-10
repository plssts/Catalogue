/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Account;
import com.j2020.model.Bank;
import com.j2020.service.BankingServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AccountController {
    private BankingServiceFactory bankingService;

    public AccountController(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping({"/{bank}/accounts/{id}", "/{bank}/accounts"})
    public ResponseEntity<List<? extends Account>> readAccounts(@PathVariable String bank, @PathVariable(required = false) String id) {
        List<? extends Account> accounts = null;

        if (!StringUtils.isAllBlank(id)) {
            Optional<String> str = Optional.of(id);
            accounts = bankingService.retrieveAccountService(Bank.valueOf(StringUtils.upperCase(bank))).retrieveAccountData(str);
        } else {
            Optional<String> str = Optional.ofNullable(null);
            accounts = bankingService.retrieveAccountService(Bank.valueOf(StringUtils.upperCase(bank))).retrieveAccountData(str);
        }

        return ok(accounts);
    }

    @GetMapping({"/{bank}/transfers/{id}", "/{bank}/transfers"})
    public ResponseEntity<String> readTransfers(@PathVariable String bank, @PathVariable(required = false) String id) {
        return new ResponseEntity<>("To be implemented", HttpStatus.NOT_IMPLEMENTED);
    }
}
