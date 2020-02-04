package com.catalogue.controller;

import com.catalogue.AccountService;
import com.catalogue.BankingService;
import com.catalogue.RevolutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private BankingService service;

    // Split to separate mappings for each bank?
    @GetMapping("{bankingService}")
    public @ResponseBody String readRevoAccount(@PathVariable String bankingService){
        return service.retrieveAccountService(bankingService).retrieveAccountData();
    }
}
