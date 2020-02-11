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

    @GetMapping({"/{bank}/accounts/{accountId}", "/{bank}/accounts"})
    public ResponseEntity<List<? extends Account>> readAccounts(@PathVariable String bank, @PathVariable(required = false) String accountId) {
        List<? extends Account> accounts = null;

        if (!StringUtils.isAllBlank(accountId)) {
            accounts = bankingService.retrieveAccountService(Bank.valueOf(StringUtils.upperCase(bank)))
                    .retrieveAccountData(Optional.of(accountId));
        } else {
            accounts = bankingService.retrieveAccountService(Bank.valueOf(StringUtils.upperCase(bank)))
                    .retrieveAccountData(Optional.ofNullable(null));
        }

        return ok(accounts);
    }

    @GetMapping({"/{bank}/transfers/{transferId}", "/{bank}/transfers"})
    public ResponseEntity<String> readTransfers(@PathVariable String bank, @PathVariable(required = false) String transferId) {
        return new ResponseEntity<>("To be implemented", HttpStatus.NOT_IMPLEMENTED);
    }
}
