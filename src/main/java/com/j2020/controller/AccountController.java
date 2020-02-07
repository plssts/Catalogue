/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.model.Bank;
import com.j2020.model.TokenFetchException;
import com.j2020.service.BankingServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AccountController {
    private BankingServiceFactory bankingService;

    public AccountController(BankingServiceFactory bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping(value="/bank/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> readRevoAccount(@PathVariable String id){
        List<Account> accounts = bankingService.retrieveAccountService(id).retrieveAccountData();
        return ok(accounts);
    }

    @GetMapping(value="/revolut/{accountId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readSpecificRevoAccount(@PathVariable String accountId){
        try {
            return bankingService.retrieveAccountService(Bank.REVOLUT).retrieveSpecificAccount(accountId);
        } catch (TokenFetchException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Padavei bloga para");
        }
    }

    @GetMapping(value="/deutsche", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readDeutAccount(){
        return bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData();
    }

    @GetMapping(value="/deutsche/{iban}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readSpecificDeutAccount(@PathVariable String iban){
        return bankingService.retrieveAccountService(Bank.DEUTSCHE).retrieveSpecificAccount(iban);
    }
}
