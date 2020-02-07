/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import com.j2020.Bank;
import com.j2020.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private BankingService service;

    @GetMapping(value="/revolut", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readRevoAccount(){
        return service.retrieveAccountService(Bank.REVOLUT).retrieveAccountData();
    }

    @GetMapping(value="/revolut/{accountId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readSpecificRevoAccount(@PathVariable String accountId){
        return service.retrieveAccountService(Bank.REVOLUT).retrieveSpecificAccount(accountId);
    }

    @GetMapping(value="/deutsche", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readDeutAccount(){
        return service.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData();
    }

    @GetMapping(value="/deutsche/{iban}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String readSpecificDeutAccount(@PathVariable String iban){
        return service.retrieveAccountService(Bank.DEUTSCHE).retrieveSpecificAccount(iban);
    }
}
