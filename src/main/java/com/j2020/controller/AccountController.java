package com.j2020.controller;

import com.j2020.Bank;
import com.j2020.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private BankingService service;

    @GetMapping("/revolut")
    public @ResponseBody String readRevoAccount(){
        return service.retrieveAccountService(Bank.REVOLUT).retrieveAccountData();
    }

    @GetMapping("/deutsche")
    public @ResponseBody String readDeutAccount(){
        return service.retrieveAccountService(Bank.DEUTSCHE).retrieveAccountData();
    }
}
