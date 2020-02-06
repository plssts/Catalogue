package com.j2020.controller;

import com.j2020.Bank;
import com.j2020.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    private BankingService service;

    @GetMapping("/revolut")
    public String readRevoAccount(){
        return service.retrieveAccountService(Bank.REVOLUT).retrieveAccountData();
    }

    @GetMapping("/revolut/{accountId}")
    public String readSpecificRevoAccount(@PathVariable String accountId){
        return service.retrieveAccountService(Bank.REVOLUT).retrieveSpecificAccount(accountId);
    }

    @GetMapping("/deutsche")
    public String readDeutAccount(@RequestParam(required = false) Map<String, String> params){
        if (params.containsKey("iban")){
            return service.retrieveAccountService(Bank.DEUTSCHE).retrieveSpecificAccount(params.get("iban"));
        } else {
            return service.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData();
        }
    }
}
