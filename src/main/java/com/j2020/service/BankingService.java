package com.j2020.service;

import com.j2020.Bank;
import com.j2020.RevolutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class BankingService {
    @Autowired
    private RevolutService revService;

    @Autowired
    private DeutscheService dbService;

    public AccountService retrieveAccountService(@PathVariable Bank bankingService){
        switch (bankingService){
            case REVOLUT:
                return revService;

            case DEUTSCHE:
                return dbService;
        }
        return null; // Should never happen since GetMapping prevents this
    }
}
